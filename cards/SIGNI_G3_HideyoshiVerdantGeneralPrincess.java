package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderCategory;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionDown;
import open.batoru.core.gameplay.actions.ActionTrash;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_G3_HideyoshiVerdantGeneralPrincess extends Card {

    public SIGNI_G3_HideyoshiVerdantGeneralPrincess()
    {
        setImageSets("WXDi-P08-044");

        setOriginalName("翠将姫　ヒデヨシ");
        setAltNames("スイショウキヒデヨシ Suishouki Hideyoshi");
        setDescription("jp",
                "@C：対戦相手のシグニの@U@E@A能力が発動する場合、このシグニの下からカード２枚をトラッシュに置いてもよい。そうした場合、代わりに発動したその能力は何もしない。\n" +
                "@C：このシグニが対戦相手の効果によって場を離れる場合、代わりにこのシグニの下からカード２枚をトラッシュに置いてもよい。そうした場合、このシグニをダウンする。\n" +
                "@E：あなたのエナゾーンからカードを４枚までこのシグニの下に置く。"
        );

        setName("en", "Hideyoshi, Jade General Queen");
        setDescription("en",
                "@C: If an @U, @E, or @A ability of your opponent's SIGNI activates, you may put two cards underneath this SIGNI into their owner's trash. If you do, the activated ability instead does nothing.\n" +
                "@C: If this SIGNI would leave the field by your opponent's effects, instead you may put two cards underneath this SIGNI into their owner's trash. If you do, down this SIGNI.\n" +
                "@E: Put up to four cards from your Ener Zone under this SIGNI."
        );
        
        setName("en_fan", "Hideyoshi, Verdant General Princess");
        setDescription("en_fan",
                "@C: If the @U, @E, or @A ability of your opponent's SIGNI would be activated, you may put 2 cards from under this SIGNI into the trash. If you do, the activated ability does nothing instead.\n" +
                "@C: If this SIGNI would leave the field by your opponent's effect, you may put 2 cards from under this SIGNI into the trash instead. If you do, down this SIGNI.\n" +
                "@E: Put up to 4 cards from your ener zone under this SIGNI."
        );

		setName("zh_simplified", "翠将姬 丰臣秀吉");
        setDescription("zh_simplified", 
                "@C 对战对手的精灵的@U@E@A能力发动的场合，可以从这只精灵的下面把2张牌放置到废弃区。这样做的场合，作为替代，发动的那个能力不能做任何事。\n" +
                "@C 这只精灵因为对战对手的效果离场的场合，作为替代，可以从这只精灵的下面把2张牌放置到废弃区。这样做的场合，这只精灵#D。\n" +
                "@E :从你的能量区把牌4张最多放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.ABILITY, OverrideScope.GLOBAL, OverrideFlag.NON_MANDATORY | OverrideFlag.PRE_OVERRIDE, this::onConstEff1ModOverrideCond,this::onConstEff1ModOverrideHandler)
            ));
            registerConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.NON_MANDATORY, this::onConstEff2ModOverrideCond,this::onConstEff2ModOverrideHandler)
            ));

            registerEnterAbility(this::onEnterEff);
        }

        private boolean onConstEff1ModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return getCardsUnderCount(CardUnderCategory.UNDER) >= 2 &&
                    !isOwnCard(event.getSourceCardIndex()) && CardType.isSIGNI(event.getSource().getCardReference().getType()) &&
                    (event.getSourceAbility() instanceof AutoAbility || event.getSourceAbility() instanceof EnterAbility || event.getSourceAbility() instanceof ActionAbility);
        }
        private void onConstEff1ModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addTargetAction(2, new TargetFilter(TargetHint.TRASH).own().under(getCardIndex())).setOnActionCompleted(() -> {
                if(list.getAction(0).getDataTable().size() == 2) for(int i=0;i<2;i++) ((ActionTrash)list.getAction(i+1)).setCardIndex((CardIndex)list.getAction(0).getDataTable().get(i));
            });
            
            list.addAction(new ActionTrash());
            list.addAction(new ActionTrash());
        }

        private boolean onConstEff2ModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return getCardsUnderCount(CardUnderCategory.UNDER) >= 2 &&
                    event.getSourceAbility() != null && !isOwnCard(event.getSource()) && !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onConstEff2ModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addTargetAction(2, new TargetFilter(TargetHint.TRASH).own().under(getCardIndex())).setOnActionCompleted(() -> {
                if(list.getAction(0).getDataTable().size() == 2) for(int i=0;i<2;i++) ((ActionTrash)list.getAction(i+1)).setCardIndex((CardIndex)list.getAction(0).getDataTable().get(i));
            });

            list.addAction(new ActionTrash());
            list.addAction(new ActionTrash());
            list.addNonMandatoryAction(new ActionDown(getCardIndex()));
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,4, new TargetFilter(TargetHint.UNDER).own().fromEner());
            attach(getCardIndex(), data, CardUnderType.UNDER_GENERIC);
        }
    }
}
