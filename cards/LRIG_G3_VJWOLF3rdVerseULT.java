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
import open.batoru.data.ability.stock.StockAbilitySLancer;

public final class LRIG_G3_VJWOLF3rdVerseULT extends Card {

    public LRIG_G3_VJWOLF3rdVerseULT()
    {
        setImageSets("SPDi43-13", "SPDi43-13P");

        setOriginalName("VJ.WOLF 3rdVerse-ULT");
        setAltNames("ブイジェーウルフサードウァ゛ースウルト Buii Jee Urufu Saado Vaasu Uruto");
        setDescription("jp",
                "@A #D：あなたのシグニ１体を対象とし、ターン終了時まで、それは【Ｓランサー】を得る。\n" +
                "@A $G1 @[@|バイブズMAX|@]@ %G0：ターン終了時まで、このルリグは@>@U $T2：あなたの効果１つによってあなたのエナゾーンにカードが合計１枚以上置かれたとき、このルリグをアップする。@@を得る。"
        );

        setName("en", "VJ.WOLF 3rd Verse-ULT");
        setDescription("en",
                "@A #D: Target 1 of your SIGNI, and until end of turn, it gains [[S Lancer]].\n" +
                "@A $G1 @[@|Vibes MAX|@]@ %G0: Until end of turn, this LRIG gains:" +
                "@>@U $T2: When 1 or more cards are put into your ener zone by one of your effects, up this LRIG."
        );

		setName("zh_simplified", "VJ.WOLF 3rdVerse-ULT");
        setDescription("zh_simplified", 
                "@A #D:你的精灵1只作为对象，直到回合结束时为止，其得到[[S枪兵]]。\n" +
                "@A $G1 氛围MAX%G0:直到回合结束时为止，这只分身得到\n" +
                "@>@U $T2 :当因为你的效果1个往你的能量区把牌合计1张以上放置时，这只分身竖直。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.WOLF);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.GREEN);
        setCost(Cost.color(CardColor.GREEN, 2));
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

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("VibesMAX");
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilitySLancer(), ChronoDuration.turnEnd());
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
            return isOwnCard(caller) && EventMove.getDataMoveLocation() == CardLocation.ENER &&
                   getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSource()) && getEvent().isAtOnce(1) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            up();
        }
    }
}
