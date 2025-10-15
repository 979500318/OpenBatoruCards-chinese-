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
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class LRIG_R3_DJLOVIT3rdVerseULT extends Card {

    public LRIG_R3_DJLOVIT3rdVerseULT()
    {
        setImageSets("SPDi43-12", "SPDi43-12P");

        setOriginalName("DJ.LOVIT 3rdVerse-ULT");
        setAltNames("ディージェーラビットサードウァ゛ースウルト Dii Jee Rabitto Saado Vaasu Uruto");
        setDescription("jp",
                "@A #D：あなたのシグニ１体を対象とし、ターン終了時まで、それは【アサシン】を得る。\n" +
                "@A $G1 @[@|バイブズMAX|@]@ %R0：ターン終了時まで、このルリグは@>@U $T2：あなたの効果１つによって対戦相手のエナゾーンからカードが合計１枚以上トラッシュに置かれたとき、このルリグをアップする。@@を得る。"
        );

        setName("en", "DJ.LOVIT 3rd Verse-ULT");
        setDescription("en",
                "@A #D: Target 1 of your SIGNI, and until end of turn, it gains [[Assassin]].\n" +
                "@A $G1 @[@|Vibes MAX|@]@ %R0: Until end of turn, this LRIG gains:" +
                "@>@U $T2: When 1 or more cards are put from your opponent's ener zone into the trash by one of your effects, up this LRIG."
        );

		setName("zh_simplified", "DJ.LOVIT 3rdVerse-ULT");
        setDescription("zh_simplified", 
                "@A #D:你的精灵1只作为对象，直到回合结束时为止，其得到[[暗杀]]。\n" +
                "@A $G1 氛围MAX%R0:直到回合结束时为止，这只分身得到\n" +
                "@>@U $T2 :当因为你的效果1个从对战对手的能量区把牌合计1张以上放置到废弃区时，这只分身竖直。@@\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LOVIT);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.RED);
        setCost(Cost.color(CardColor.RED, 2));
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

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.RED, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
            act2.setName("VibesMAX");
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityAssassin(), ChronoDuration.turnEnd());
        }

        private void onActionEff2()
        {
            AutoAbility attachedAuto = new AutoAbility(GameEventId.TRASH, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachedAuto.setUseLimit(UseLimit.TURN, 2);
            attachedAuto.getFlags().addValue(AbilityFlag.ACTIVE_ONCE_PER_EFFECT);

            attachAbility(getCardIndex(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && caller.isEffectivelyAtLocation(CardLocation.ENER) &&
                    getEvent().getSourceAbility() != null && isOwnCard(getEvent().getSource()) && getEvent().isAtOnce(1) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex caller)
        {
            up();
        }
    }
}
