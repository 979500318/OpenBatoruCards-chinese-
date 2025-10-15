package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K3_HedonimDissonaNaturalBacteriaPrincess extends Card {

    public SIGNI_K3_HedonimDissonaNaturalBacteriaPrincess()
    {
        setImageSets("WXDi-P13-055");
        setLinkedImageSets("WXDi-P13-010");

        setOriginalName("羅菌姫　ヘドニム//ディソナ");
        setAltNames("ラキンヒメヘドニムディソナ Rakinhime Hedonimu Disona");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたの場に《ナナシ　其ノ参ノ禍》がいる場合、対戦相手のデッキの上からカードを４枚トラッシュに置く。\n" +
                "@E %K：あなたのトラッシュから#Sのシグニ１枚を対象とし、それを場に出す。\n" +
                "@A #C #C #C #C #C：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－12000する。" +
                "~#：あなたのトラッシュからカード１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Hedonym//Dissona, Bacteria Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if \"Nanashi, Part Three Calamity\" is on your field, put the top four cards of your opponent's deck into their trash.\n@E %K: Put target #S SIGNI from your trash onto your field.\n@A #C #C #C #C #C: Target SIGNI on your opponent's field gets --12000 power until end of turn." +
                "~#Add target card from your trash to your hand."
        );
        
        setName("en_fan", "Hedonim//Dissona, Natural Bacteria Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if your LRIG is \"Nanashi, Part Three Calamity\", put the top 4 cards of your opponent's deck into the trash.\n" +
                "@E %K: Target 1 #S @[Dissona]@ SIGNI from your trash, and put it onto the field.\n" +
                "@A #C #C #C #C #C: Target 1 of your opponent's SIGNI, and until end of turn, it gets --12000 power." +
                "~#Target 1 card from your trash, and add it to your hand."
        );

		setName("zh_simplified", "罗菌姬 享乐//失调");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，你的场上有《ナナシ　其ノ参ノ禍》的场合，从对战对手的牌组上面把4张牌放置到废弃区。\n" +
                "@E %K从你的废弃区把#S的精灵1张作为对象，将其出场。\n" +
                "@A #C #C #C #C #C:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-12000。" +
                "~#从你的废弃区把1张牌作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST | CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);

            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
            
            registerActionAbility(new CoinCost(5), this::onActionEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond(CardIndex cardIndex)
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex cardIndex)
        {
            if(getLRIG(getOwner()).getIndexedInstance().getName().getValue().contains("ナナシ　其ノ参ノ禍"))
            {
                millDeck(getOpponent(), 4);
            }
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().dissona().fromTrash().playable()).get();
            putOnField(target);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -12000, ChronoDuration.turnEnd());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromTrash()).get();
            addToHand(target);
        }
    }
}
