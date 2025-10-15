package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;

public final class ARTS_G_WithACalmAndOpenMind extends Card {

    public ARTS_G_WithACalmAndOpenMind()
    {
        setImageSets("WX24-P2-007", "WX24-P2-007U");

        setOriginalName("虚心坦懐");
        setAltNames("トゥルーハート Turuu Haato True Heart");
        setDescription("jp",
                "あなたのエナゾーンからあなたのセンタールリグと共通する色を持つシグニを３枚まで対象とし、それらを場に出す。ターン終了時まで、あなたのすべてのシグニは@>@U：このシグニがアタックしたとき、対戦相手のシグニ１体を対象とし、それのレベル１につき%Xを支払ってもよい。そうした場合、それをエナゾーンに置く。@@を得る。\n" +
                "&E４枚以上@0追加でターン終了時まで、あなたのすべてのシグニは@>@U：アタックフェイズ開始時、【エナチャージ１】をする。@@を得る。"
        );

        setName("en", "With a Calm and Open Mind");
        setDescription("en",
                "Target up to 3 SIGNI that share a common color with your center LRIG from your ener zone, and put them onto the field. Until end of turn, all of your SIGNI gain:" +
                "@>@U: Whenever this SIGNI attacks, target 1 of your opponent's SIGNI, and you may pay %X for each of its levels. If you do, put it into the ener zone.@@" +
                "&E4 or more@0 Additionally, until end of turn, all of your SIGNI gain:" +
                "@>@U: At the beginning of your attack phase, [[Ener Charge 1]]."
        );

		setName("zh_simplified", "虚心坦怀");
        setDescription("zh_simplified", 
                "从你的能量区把持有与你的核心分身共通颜色的精灵3张最多作为对象，将这些出场。直到回合结束时为止，你的全部的精灵得到\n" +
                "@>@U 当这只精灵攻击时，对战对手的精灵1只作为对象，可以依据其的等级的数量，每有1级就把%X:支付。这样做的场合，将其放置到能量区。@@\n" +
                "&E4张以上@0追加直到回合结束时为止，你的全部的精灵得到\n" +
                "@>@U :攻击阶段开始时，[[能量填充1]]。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerARTSAbility(this::onARTSEff).setRecollect(4);
        }

        private void onARTSEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,3, new TargetFilter(TargetHint.FIELD).own().SIGNI().withColor(getLRIG(getOwner()).getIndexedInstance().getColor()).fromEner().playable());
            putOnField(data);

            forEachSIGNIOnField(getOwner(), cardIndex -> {
                AutoAbility attachedAuto1 = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff1);
                attachAbility(cardIndex, attachedAuto1, ChronoDuration.turnEnd());
            });

            if(getAbility().isRecollectFulfilled())
            {
                forEachSIGNIOnField(getOwner(), cardIndex -> {
                    AutoAbility attachedAuto2 = new AutoAbility(GameEventId.PHASE_START, this::onAttachedAutoEff2);
                    attachedAuto2.setCondition(this::onAttachedAutoEffCond2);
                    attachedAuto2.setNestedDescriptionOffset(1);
                    attachAbility(cardIndex, attachedAuto2, ChronoDuration.turnEnd());
                });
            }
        }
        private void onAttachedAutoEff1()
        {
            CardIndex source = getAbility().getSourceCardIndex();
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ENER).OP().SIGNI()).get();
            
            if(target != null && source.getIndexedInstance().payEner(Cost.colorless(target.getIndexedInstance().getLevel().getValue())))
            {
                source.getIndexedInstance().putInEner(target);
            }
        }
        private ConditionState onAttachedAutoEffCond2()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.ATTACK_PRE ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff2(CardIndex caller)
        {
            enerCharge(1);
        }
    }
}
