package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.DownCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.events.EventMove;

public final class LRIG_W3_MCLion3rdVerseULT extends Card {

    public LRIG_W3_MCLion3rdVerseULT()
    {
        setImageSets("SPDi43-11", "SPDi43-11P");

        setOriginalName("MC.LION 3rdVerse-ULT");
        setAltNames("エムシーリオンサードウァ゛ースウルト Emu Shii Rion Saado Vaasu Uruto");
        setDescription("jp",
                "@A #D：対戦相手のシグニ１体を対象とし、それを手札に戻す。\n" +
                "@A $G1 @[@|バイブズMAX|@]@ %W0：ターン終了時まで、このルリグは@>@U $T2：あなたの効果１つによってカードが合計１枚以上あなたの手札に移動したとき、このルリグをアップする。@@を得る。"
        );

        setName("en", "MC.LION 3rd Verse-ULT");
        setDescription("en",
                "@A #D: Target 1 of your opponent's SIGNI, and return it to their hand.\n" +
                "@A $G1 @[@|Vibes MAX|@]@ %W0: Until end of turn, this LRIG gains:" +
                "@>@U $T2: When 1 or more cards are moved to your hand by one of your effects, up this LRIG."
        );

		setName("zh_simplified", "MC.LION 3rdVerse-ULT");
        setDescription("zh_simplified", 
                "@A 横置:对战对手的精灵1只作为对象，将其返回手牌。\n" +
                "@A $G1 氛围MAX%W0:直到回合结束时为止，这只分身得到\n" +
                "@>@U $T2 :当因为你的效果1个把牌合计1张以上往你的手牌移动时，这只分身竖直。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
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

            registerActionAbility(new DownCost(), this::onActionEff1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.WHITE, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("VibesMAX");
        }
        
        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI()).get();
            addToHand(target);
        }

        private void onActionEff2()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.MOVE, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setUseLimit(UseLimit.TURN, 2);
            attachedAuto.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);
            
            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return isOwnCard(caller) && EventMove.getDataMoveLocation() == CardLocation.HAND &&
                   getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSource()) && getEvent().isAtOnce(1) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            up();
        }
    }
}
