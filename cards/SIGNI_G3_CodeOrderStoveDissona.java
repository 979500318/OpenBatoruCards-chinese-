package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.*;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G3_CodeOrderStoveDissona extends Card {

    public SIGNI_G3_CodeOrderStoveDissona()
    {
        setImageSets("WXDi-P12-052");
        setLinkedImageSets("WXDi-P12-009");

        setOriginalName("コードオーダー　ズットモ//ディソナ");
        setAltNames("コードオーダーズットモディソナ Koodo Ooda Zuttomo Disona");
        setDescription("jp",
                "@C：あなたの場に《メル＝椿姫》がいるかぎり、このシグニのパワーはこのシグニの下にあるカード１枚につき＋5000される。\n" +
                "@U：あなたのアタックフェイズ開始時、このシグニの下から#Sのカードを１枚までエナゾーンに置く。\n" +
                "@E：あなたのデッキの上からカードを２枚このシグニの下に置く。\n" +
                "@A #C #C #C #C #C：ターン終了時まで、このシグニは【ランサー】を得る。" +
                "~#：対戦相手のパワー7000以上のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "BFF//Dissona, Code: Order");
        setDescription("en",
                "@C: As long as \"Mel - Lady of the Camellias\" is on your field, this SIGNI gets +5000 power for each card underneath this SIGNI.\n@U: At the beginning of your attack phase, put up to one #S card underneath this SIGNI into its owner's Ener Zone.\n@E: Put the top two cards of your deck under this SIGNI. \n@A #C #C #C #C #C: This SIGNI gains [[Lancer]] until end of turn." +
                "~#Vanish target SIGNI on your opponent's field with power 7000 or more."
        );
        
        setName("en_fan", "Code Order Stove//Dissona");
        setDescription("en_fan",
                "@C: As long as there is \"Mel-Camellia Princess\" on your field, this SIGNI gets +5000 power for each card under this SIGNI.\n" +
                "@U: At the beginning of your attack phase, put up to 1 #S @[Dissona]@ card from under this SIGNI into the ener zone.\n" +
                "@E: Put the top 2 cards of your deck under this SIGNI.\n" +
                "@A #C #C #C #C #C: Until end of turn, this SIGNI gains [[Lancer]]." +
                "~#Target 1 of your opponent's SIGNI with power 7000 or more, and banish it."
        );

		setName("zh_simplified", "点单代号 结交//失调");
        setDescription("zh_simplified", 
                "@C :你的场上有《メル＝椿姫》时，这只精灵的力量依据这只精灵的下面的牌的数量，每有1张就+5000。\n" +
                "@U 你的攻击阶段开始时，从这只精灵的下面把#S的牌1张最多放置到能量区。\n" +
                "@E :从你的牌组上面把2张牌放置到这只精灵的下面。（表向放置）\n" +
                "@A #C #C #C #C #C:直到回合结束时为止，这只精灵得到[[枪兵]]。" +
                "~#对战对手的力量7000以上的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(this::onConstEffModGetValue));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new CoinCost(5), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("メル＝椿姫") ? ConditionState.OK : ConditionState.BAD;
        }
        private double onConstEffModGetValue(CardIndex cardIndex)
        {
            return 5000 * getCardsUnderCount(CardUnderCategory.UNDER);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            CardIndex target = playerTargetCard(0,1, new TargetFilter(TargetHint.ENER).own().dissona().under(getCardIndex())).get();
            putInEner(target);
        }
        
        private void onEnterEff()
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC, 2);
        }
        
        private void onActionEff()
        {
            attachAbility(getCardIndex(), new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI().withPower(7000,0)).get();
            banish(target);
        }
    }
}
