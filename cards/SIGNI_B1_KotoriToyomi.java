package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_B1_KotoriToyomi extends Card {

    public SIGNI_B1_KotoriToyomi()
    {
        setImageSets("WX25-CP1-062");

        setOriginalName("豊見コトリ");
        setAltNames("トヨミコトリ Toyomi Kotori");
        setDescription("jp",
                "@U：あなたのターン終了時、手札を１枚捨ててもよい。そうした場合、次の対戦相手のターン終了時まで、あなたのすべての＜ブルアカ＞のシグニのパワーを＋4000する。" +
                "~{{U：あなたのアタックフェイズ開始時、アップ状態のこのシグニをダウンしてもよい。そうした場合、カードを１枚引く。"
        );

        setName("en", "Toyomi Kotori");

        setName("en_fan", "Kotori Toyomi");
        setDescription("en",
                "@U: At the end of your turn, you may discard 1 card from your hand. If you do, until the end of your opponent's next turn, all of your <<Blue Archive>> SIGNI get +4000 power." +
                "~{{U: At the beginning of your attack phase, you may down this SIGNI. If you do, draw 1 card."
        );

		setName("zh_simplified", "丰见亚都梨");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，可以把手牌1张舍弃。这样做的场合，直到下一个对战对手的回合结束时为止，你的全部的<<ブルアカ>>精灵的力量+4000。\n" +
                "~{{U你的攻击阶段开始时，可以把竖直状态的这只精灵#D。这样做的场合，抽1张牌。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(1);
        setPower(3000);

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
            auto1.setCondition(this::onAutoEff1Cond);

            AutoAbility auto2 = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff2);
            auto2.setCondition(this::onAutoEff2Cond);
            auto2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEff1Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff1(CardIndex caller)
        {
            if(discard(0,1).get() != null)
            {
                gainPower(new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.BLUE_ARCHIVE).getExportedData(), 4000, ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }

        private ConditionState onAutoEff2Cond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff2(CardIndex caller)
        {
            if(getCardIndex().isSIGNIOnField() && playerChoiceActivate() && down())
            {
                draw(1);
            }
        }
    }
}
