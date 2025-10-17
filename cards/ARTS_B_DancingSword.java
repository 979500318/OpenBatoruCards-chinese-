package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.ARTSAbility;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.events.EventDamage;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.gfx.GFXZoneWall;

public final class ARTS_B_DancingSword extends Card {

    public ARTS_B_DancingSword()
    {
        setImageSets("WX25-P2-006", "WX25-P2-006U");

        setOriginalName("ダンシング・ソード");
        setAltNames("ダンシングソード Danshingu Soodo");
        setDescription("jp",
                "このアーツを使用する際、あなたのルリグデッキからアーツ１枚をルリグトラッシュに置いてもよい。そうした場合、このアーツの使用コストは%X %X %X減る。\n\n" +
                "このターン、あなたがシグニによってダメージを受ける場合、代わりに手札を１枚捨ててもよい。\n" +
                "&E４枚以上@0追加で、ターン終了時まで、対戦相手のすべてのシグニは@>@U $T1：このシグニがアタックしたとき、対戦相手はカードを１枚引く。@@を得る。"
        );

        setName("en", "Dancing Sword");
        setDescription("en",
                "While using this ARTS, you may put 1 ARTS from your LRIG deck into the LRIG trash. If you do, the use cost of this ARTS is reduced by %X %X %X.\n\n" +
                "This turn, if you would be damaged by a SIGNI, you may discard 1 card from your hand instead." +
                "&E4 or more@0 Additionally, until end of turn, all of your opponent's SIGNI gain:" +
                "@>@U $T1: When this SIGNI attacks, your opponent draws 1 card."
        );

		setName("zh_simplified", "舞动·刀剑");
        setDescription("zh_simplified", 
                "这张必杀使用时，可以从你的分身牌组把必杀1张放置到分身废弃区。这样做的场合，这张必杀的使用费用减%X %X %X。\n" +
                "这个回合，你因为精灵受到伤害的场合，作为替代，可以把手牌1张舍弃。\n" +
                "&E4张以上@0追加，直到回合结束时为止，对战对手的全部的精灵得到\n" +
                "@>@U $T1 :当这只精灵攻击时，对战对手抽1张牌。@@\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.colorless(4));
        setUseTiming(UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ARTSAbility arts = registerARTSAbility(this::onARTSEff);
            arts.setReductionCost(new TrashCost(new TargetFilter().own().ARTS().except(cardId).fromLocation(CardLocation.DECK_LRIG)), Cost.colorless(3));
            arts.setRecollect(4);
        }

        private void onARTSEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.DAMAGE, OverrideScope.GLOBAL, OverrideFlag.NON_MANDATORY | OverrideFlag.PRESERVE_SOURCES, this::onAttachedConstEffModOverrideCond, this::onAttachedConstEffModOverrideHandler))
            );
            attachedConst.setCondition(this::onAttachedConstEffCond);
            GFXZoneWall.attachToAbility(attachedConst, new GFXZoneWall(getOwner(), CardLocation.LIFE_CLOTH, "LRIG", new int[]{50,150,205}));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            
            if(getAbility().isRecollectFulfilled())
            {
                forEachSIGNIOnField(getOpponent(), cardIndex -> {
                    AutoAbility attachedAuto = new AutoAbility(GameEventId.ATTACK, this::onAttachedAutoEff);
                    attachedAuto.setUseLimit(UseLimit.TURN, 1);
                    attachAbility(cardIndex, attachedAuto, ChronoDuration.turnEnd());
                });
            }
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getHandCount(getOwner()) >= 1 ? ConditionState.OK : ConditionState.BAD;
        }
        private boolean onAttachedConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return ((EventDamage)event).getPlayer() == sourceAbilityRC.getAbilityOwner() &&
                   CardType.isSIGNI(event.getSource().getCardReference().getType());
        }
        private void onAttachedConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addDiscardAction(1);
        }
        private void onAttachedAutoEff()
        {
            CardIndex cardIndex = getAbility().getSourceCardIndex();
            cardIndex.getIndexedInstance().draw(cardIndex.getIndexedInstance().getOpponent(), 1);
        }
    }
}
