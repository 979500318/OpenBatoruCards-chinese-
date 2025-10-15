package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.cost.TrashCost;

public final class LRIG_K3_MachinaThreeNEO extends Card {

    public LRIG_K3_MachinaThreeNEO()
    {
        setImageSets("SPDi43-05", "SPDi43-05P");

        setOriginalName("マキナ・スリーNEO");
        setAltNames("マキナスリーネオ Makina Surii Neo");
        setDescription("jp",
                "@A %K0：あなたのシグニ１体を対象とし、このルリグの下からカード１枚をそれの【ソウル】にする。\n" +
                "@A $G1 @[@|バーテックス|@]@ @[ルリグデッキからアーツ１枚をルリグトラッシュに置く]@：次の対戦相手のターン終了時まで、このルリグは@>@U：対戦相手のルリグかシグニ１体がアタックしたとき、あなたの場かエナゾーンからそのルリグかシグニと同じレベルのシグニ１枚をトラッシュに置いてもよい。そうした場合、そのアタックを無効にする。@@を得る。"
        );

        setName("en", "Machina Three NEO");
        setDescription("en",
                "@A %K0: Target 1 of your SIGNI, and attach 1 card under this LRIG to it as a [[Soul]].\n" +
                "@A $G1 @[@|Vertex|@]@ @[Put 1 ARTS from the LRIG deck into the LRIG trash]@: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@U: Whenever 1 of your opponent's LRIG or SIGNI attacks, you may put 1 SIGNI with the same level as that LRIG or SIGNI from your field or ener zone into the trash. If you do, disable that attack."
        );

		setName("zh_simplified", "玛琪娜·叁NEO");
        setDescription("zh_simplified", 
                "@A %K0:你的精灵1只作为对象，从这只分身的下面把1张牌作为其的[[灵魂]]。\n" +
                "@A $G1 顶点从分身牌组把必杀1张放置到分身废弃区:直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@U :当对战对手的分身或精灵1只攻击时，可以从你的场上或能量区把与那只分身或精灵相同等级的精灵1张放置到废弃区。这样做的场合，那次攻击无效。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MACHINA);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff1);
            act1.setCondition(this::onActionEff1Cond);

            ActionAbility act2 = registerActionAbility(new TrashCost(new TargetFilter().ARTS()), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("Vertex");
        }

        private ConditionState onActionEff1Cond()
        {
            return new TargetFilter().own().under(getCardIndex()).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().SIGNI().attachable(CardUnderType.ATTACHED_SOUL)).get();

            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ATTACH).own().under(getCardIndex())).get();

                attach(target, cardIndex, CardUnderType.ATTACHED_SOUL);
            }
        }

        private void onActionEff2()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.TRASH).own().SIGNI().withLevel(caller.getIndexedInstance().getLevel().getValue()).or(new TargetFilter().fromField(), new TargetFilter().fromEner())).get();

            if(trash(cardIndex))
            {
                disableNextAttack(caller);
            }
        }
    }
}

