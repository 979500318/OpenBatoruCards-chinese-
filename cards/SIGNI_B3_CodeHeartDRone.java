package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B3_CodeHeartDRone extends Card {

    public SIGNI_B3_CodeHeartDRone()
    {
        setImageSets("WX24-P2-053");

        setOriginalName("コードハート　Dローン");
        setAltNames("コードハートディーローン Koodo Haato Dii Roon Drone");
        setDescription("jp",
                "@U：あなたのアタックフェイズ開始時、このターンにあなたがスペルを使用していた場合、対戦相手は手札を１枚捨てる。\n" +
                "@U：このシグニがアタックしたとき、対戦相手は手札を１枚捨てる。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1対戦相手のシグニを２体まで対象とし、それらをダウンする。\n" +
                "$$2カードを１枚引く。"
        );

        setName("en", "Code Heart D Rone");
        setDescription("en",
                "@U: At the beginning of your attack phase, if you used a spell this turn, your opponent discards 1 card from their hand.\n" +
                "@U: Whenever this SIGNI attacks, your opponent discards 1 card from their hand." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Target up to 2 of your opponent's SIGNI, and down them.\n" +
                "$$2 Draw 1 card."
        );

		setName("zh_simplified", "爱心代号 无人机");
        setDescription("zh_simplified", 
                "@U :你的攻击阶段开始时，这个回合你把魔法使用过的场合，对战对手把手牌1张舍弃。\n" +
                "@U :当这只精灵攻击时，对战对手把手牌1张舍弃。" +
                "~#以下选1种。\n" +
                "$$1 对战对手的精灵2只最多作为对象，将这些#D。\n" +
                "$$2 抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
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
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond1()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }

        private void onAutoEff1(CardIndex caller)
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.USE_SPELL && isOwnCard(event.getCaller())) > 0)
            {
                discard(getOpponent(), 1);
            }
        }

        private void onAutoEff2()
        {
            discard(getOpponent(), 1);
        }

        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
                down(data);
            } else {
                draw(1);
            }
        }
    }
}
