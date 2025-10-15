package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.CardDataImageSet.Mask;

public final class LRIGA_K2_IoriShiromiSwimsuit extends Card {

    public LRIGA_K2_IoriShiromiSwimsuit()
    {
        setImageSets(Mask.PORTRAIT_OFFSET_RIGHT+"WXDi-CP02-048");

        setOriginalName("銀鏡イオリ(水着)");
        setAltNames("シロミイオリミズギ Shiromi Iori Mizugi");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それのパワーを－8000し、それは@>@U：アタックフェイズ終了時、ターン終了時まで、このシグニのパワーを－5000する。@@を得る。" +
                "~{{E：あなたのトラッシュから＜ブルアカ＞のカード１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Shiromi Iori (Swimsuit)");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gets --8000 power and gains@>@U: At the end of the attack phase, this SIGNI gets --5000 power until end of turn.@@until end of turn.~{{E: Add target <<Blue Archive>> card from your trash to your hand."
        );
        
        setName("en_fan", "Iori Shiromi (Swimsuit)");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gets --8000 power, and it gains:" +
                "@>@U: At the end of the attack phase, until end of turn, this SIGNI gets --5000 power.@@" +
                "~{{E: Target 1 <<Blue Archive>> card from your trash, and add it to your hand."
        );

		setName("zh_simplified", "银镜伊织(泳装)");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其的力量-8000，其得到\n" +
                "@>@U :攻击阶段结束时，直到回合结束时为止，这只精灵的力量-5000。@@\n" +
                "~{{E:从你的废弃区把<<ブルアカ>>牌1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.PREFECT_TEAM);
        setColor(CardColor.BLACK);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);

            EnterAbility enter2 = registerEnterAbility(this::onEnterEff2);
            enter2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null)
            {
                gainPower(target, -8000, ChronoDuration.turnEnd());

                AutoAbility attachedAuto = new AutoAbility(GameEventId.PHASE_END, this::onAttachedAutoEff);
                attachedAuto.setCondition(this::onAttachedAutoEffCond);
                attachAbility(target, attachedAuto, ChronoDuration.turnEnd());
            }
        }
        private ConditionState onAttachedAutoEffCond()
        {
            return getCurrentPhase() == GamePhase.ATTACK_LRIG ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            getAbility().getSourceCardIndex().getIndexedInstance().gainPower(getAbility().getSourceCardIndex(), -5000, ChronoDuration.turnEnd());
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash()).get();
            addToHand(target);
        }
    }
}

