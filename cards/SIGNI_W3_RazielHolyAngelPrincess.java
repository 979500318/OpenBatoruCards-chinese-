package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_W3_RazielHolyAngelPrincess extends Card {

    public SIGNI_W3_RazielHolyAngelPrincess()
    {
        setImageSets("WX24-P3-048");

        setOriginalName("聖天姫　ラジエル");
        setAltNames("セイテンキラジエル Seitenki Rajieru");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、あなたのアップ状態の白のシグニを好きな数ダウンする。その後、レベルがこの方法でダウンしたシグニの数以下の対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@U：あなたのターン終了時、あなたの他のシグニ１体を対象とし、次の対戦相手のターン終了時まで、それのパワーを＋2000する。"
        );

        setName("en", "Raziel, Holy Angel Princess");
        setDescription("en",
                "@U: At the beginning of your attack phase, down any number of your upped white SIGNI. Then, target 1 of your opponent's SIGNI with level equal to or less than the number of SIGNI downed this way, and return it to their hand.\n" +
                "@U: At the end of your turn, target 1 of your other SIGNI, and until the end of your opponent's next turn, it gets +2000 power."
        );

		setName("zh_simplified", "圣天姬 拉结尔");
        setDescription("zh_simplified", 
                "@U 你的攻击阶段开始时，你的竖直状态的白色的精灵任意数量#D。然后，等级在这个方法#D的精灵的数量以下的对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@U :你的回合结束时，你的其他的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其的力量+2000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ANGEL);
        setLevel(3);
        setPower(10000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            AutoAbility auto1 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff1);
            auto1.setCondition(this::onAutoEffCond1);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEffCond2);
        }

        private ConditionState onAutoEffCond1()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.DOWN).own().SIGNI().withColor(CardColor.WHITE).upped());
            int count = down(data);
            
            if(count > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withLevel(0,count)).get();
                addToHand(target);
            }
        }
        
        private ConditionState onAutoEffCond2()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.PLUS).own().SIGNI().except(getCardIndex())).get();
            gainPower(target, 2000, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
