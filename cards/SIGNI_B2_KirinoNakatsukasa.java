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
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_B2_KirinoNakatsukasa extends Card {

    public SIGNI_B2_KirinoNakatsukasa()
    {
        setImageSets("WX25-CP1-067");

        setOriginalName("中務キリノ");
        setAltNames("ナカツカサキリノ Nakatsukasa Kirino");
        setDescription("jp",
                "@U：対戦相手のアタックフェイズ開始時、手札から＜ブルアカ＞のカードを１枚捨ててもよい。そうした場合、ターン終了時まで、対戦相手のすべてのシグニは@>@U：このシグニがアタックしたとき、あなたは手札を１枚捨てる。@@を得る。" +
                "~{{C：このシグニのパワーは＋4000される。"
        );

        setName("en", "Nakatsukasa Kirino");

        setName("en_fan", "Kirino Nakatsukasa");
        setDescription("en",
                "@U: At the beginning of your opponent's attack phase, you may discard 1 <<Blue Archive>> card from your hand. If you do, until end of turn, all of your opponent's SIGNI gain:" +
                "@>@U: Whenever this SIGNI attacks, discard 1 card from your hand.@@" +
                "~{{C: This SIGNI gets +4000 power."
        );

		setName("zh_simplified", "中务桐乃");
        setDescription("zh_simplified", 
                "@U :对战对手的攻击阶段开始时，可以从手牌把<<ブルアカ>>牌1张舍弃。这样做的场合，直到回合结束时为止，对战对手的全部的精灵得到\n" +
                "@>@U :当这只精灵攻击时，你把手牌1张舍弃。@@\n" +
                "~{{C:这只精灵的力量+4000。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BLUE_ARCHIVE);
        setLevel(2);
        setPower(8000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
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

            ConstantAbility cont = registerConstantAbility(new PowerModifier(4000));
            cont.getFlags().addValue(AbilityFlag.BONDED);
        }

        private ConditionState onAutoEffCond()
        {
            return !isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(discard(0,1, new TargetFilter().withClass(CardSIGNIClass.BLUE_ARCHIVE)).get() != null)
            {
                forEachSIGNIOnField(getOpponent(), cardIndex -> {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachAbility(cardIndex, attachedAuto, ChronoDuration.turnEnd());
                });
            }
        }
        private void onAttachedAutoEff()
        {
            getAbility().getSourceCardIndex().getIndexedInstance().discard(1);
        }
    }
}
