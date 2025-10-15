package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.CoinCost;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_K3_NanashiFessoneNaturalBacteriaPrincess extends Card {

    public SIGNI_K3_NanashiFessoneNaturalBacteriaPrincess()
    {
        setImageSets("WXDi-P14-049", "WXDi-P14-049P");

        setOriginalName("羅菌姫　ナナシ//フェゾーネ");
        setAltNames("ラキンヒメナナシフェゾーネ Rakinhime Nanashi Fezoone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このシグニが覚醒状態の場合、対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000する。\n" +
                "@E @[アップ状態のルリグ２体をダウンする]@：このシグニは覚醒する。\n" +
                "@A #D：あなたのデッキの上からカードを３枚トラッシュに置く。\n" +
                "@A $T2 #C：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－2000する。"
        );

        setName("en", "Nanashi//Fesonne, Bacteria Queen");
        setDescription("en",
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, target SIGNI on your opponent's field gets --8000 power until end of turn.\n@E @[Down two upped LRIG]@: Awaken this SIGNI. \n@A #D: Put the top three cards of your deck into your trash.\n@A $T2 #C: Target SIGNI on your opponent's field gets --2000 power until end of turn."
        );
        
        setName("en_fan", "Nanashi//Fessone, Natural Bacteria Princess");
        setDescription("en_fan",
                "@U: At the beginning of your attack phase, if this SIGNI is awakened, target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power.\n" +
                "@E @[Down 2 of your upped LRIG]@: This SIGNI awakens.\n" +
                "@A #D: Put the top 3 cards of your deck into the trash.\n" +
                "@A $T2 #C: Target 1 of your opponent's SIGNI, and until end of turn, it gets --2000 power."
        );

		setName("zh_simplified", "罗菌姬 无名//音乐节");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这只精灵在觉醒状态的场合，对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000。\n" +
                "@E 竖直状态的分身2只#D:这只精灵觉醒。\n" +
                "@A #D:从你的牌组上面把3张牌放置到废弃区。\n" +
                "@A $T2 #C:对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BACTERIA);
        setLevel(3);
        setPower(10000);

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
            
            registerEnterAbility(new DownCost(2, new TargetFilter().anyLRIG()), this::onEnterEff);
            
            registerActionAbility(new DownCost(), this::onActionEff1);

            ActionAbility act2 = registerActionAbility(new CoinCost(1), this::onActionEff2);
            act2.setUseLimit(UseLimit.TURN, 2);
        }
        
        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(isState(CardStateFlag.AWAKENED))
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                gainPower(target, -8000, ChronoDuration.turnEnd());
            }
        }
        
        private void onEnterEff()
        {
            getCardStateFlags().addValue(CardStateFlag.AWAKENED);
        }
        
        private void onActionEff1()
        {
            millDeck(3);
        }
        
        private void onActionEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            gainPower(target, -2000, ChronoDuration.turnEnd());
        }
    }
}
