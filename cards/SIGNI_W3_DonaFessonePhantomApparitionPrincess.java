package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_DonaFessonePhantomApparitionPrincess extends Card {

    public SIGNI_W3_DonaFessonePhantomApparitionPrincess()
    {
        setImageSets("WXDi-P14-041", "WXDi-P14-041P");

        setOriginalName("幻怪姫　ドーナ//フェゾーネ");
        setAltNames("ゲンカイキドーナフェゾーネ Genkaiki Doona Fezoone");
        setDescription("jp",
                "@C：対戦相手のターンの間、このシグニは覚醒状態であるかぎり、【シャドウ】を得る。\n" +
                "@U：このシグニがアタックしたとき、あなたのトラッシュからシグニ１枚を対象とし、%X %Xを支払ってもよい。そうした場合、それを手札に加える。\n" +
                "@E @[アップ状態のルリグ２体をダウンする]@：このシグニは覚醒する。\n" +
                "@A $T1 #C #C：カードを１枚引く。"
        );

        setName("en", "Dona//Fesonne, Phantom Spirit Queen");
        setDescription("en",
                "@C: During your opponent's turn, as long as this SIGNI is awakened, it gains [[Shadow]].\n@U: Whenever this SIGNI attacks, you may pay %X %X. If you do, add target SIGNI from your trash to your hand.\n@E @[Down two upped LRIG]@: Awaken this SIGNI. \n@A $T1 #C #C: Draw a card."
        );
        
        setName("en_fan", "Dona//Fessone, Phantom Apparition Princess");
        setDescription("en_fan",
                "@C: During your opponent's turn, as long as this SIGNI is awakened, it gains [[Shadow]].\n" +
                "@U: Whenever this SIGNI attacks, target 1 SIGNI from your trash, and you may pay %X %X. If you do, add it to your hand.\n" +
                "@E @[Down 2 of your upped LRIG]@: This SIGNI awakens.\n" +
                "@A $T1 #C #C: Draw 1 card."
        );

		setName("zh_simplified", "幻怪姬 多娜//音乐节");
        setDescription("zh_simplified", 
                "@C :对战对手的回合期间，这只精灵在觉醒状态时，得到[[暗影]]。\n" +
                "@U :当这只精灵攻击时，从你的废弃区把精灵1张作为对象，可以支付%X %X。这样做的场合，将其加入手牌。\n" +
                "@E 竖直状态的分身2只横置:这只精灵觉醒。\n" +
                "@A $T1 #C #C:抽1张牌。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.APPARITION);
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
            
            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);

            registerEnterAbility(new DownCost(2, new TargetFilter().anyLRIG()), this::onEnterEff);

            ActionAbility act = registerActionAbility(new CoinCost(2), this::onActionEff);
            act.setUseLimit(UseLimit.TURN, 1);
        }
        
        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() && isState(CardStateFlag.AWAKENED) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow());
        }
        
        private void onAutoEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            
            if(target != null && payEner(Cost.colorless(2)))
            {
                addToHand(target);
            }
        }

        private void onEnterEff()
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }

        private void onActionEff()
        {
            draw(1);
        }
    }
}
