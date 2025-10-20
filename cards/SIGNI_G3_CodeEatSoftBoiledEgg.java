package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameAction;
import open.batoru.core.gameplay.GameConst;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.actions.ActionDown;
import open.batoru.core.gameplay.actions.ActionExclude;
import open.batoru.core.gameplay.actions.override.OverrideAction;
import open.batoru.core.gameplay.actions.override.OverrideAction.OverrideScope;
import open.batoru.core.gameplay.actions.override.OverrideActionList;
import open.batoru.core.gameplay.actions.override.OverrideActionList.OverrideFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.events.GameEvent;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_G3_CodeEatSoftBoiledEgg extends Card {

    public SIGNI_G3_CodeEatSoftBoiledEgg()
    {
        setImageSets("WXDi-P09-TK03A");

        setOriginalName("コードイート オンタマ");
        setAltNames("コードイートオンタマ Koodo Iito Ontama");
        setDescription("jp",
                "((このクラフトは効果以外によっては場に出せない))\n" +
                "((【アクセ】はシグニ１体に１枚までしか付けられない。このクラフトが付いているシグニが場を離れるとこのクラフトは除外される))\n\n" +
                "@C：これにアクセされているシグニが場を離れる場合、代わりにこれをゲームから除外してもよい。そうした場合、そのシグニをダウンする。"
        );

        setName("en", "Soft-Boiled Egg, Code: Eat");
        setDescription("en",
                "((This Craft can only be put onto the field by an effect.))\n" +
                "((Up to one [[Acce]] can be attached to a SIGNI, and it is removed from the game when that SIGNI leaves the field.))\n\n" +
                "@C: If the SIGNI with this attached to it as an [[Acce]] would leave the field, you may instead remove this from the game. If you do, down it."
        );
        
        setName("en_fan", "Code Eat Soft-Boiled Egg");
        setDescription("en_fan",
                "((This craft cannot enter the field other than by effects.))\n" +
                "((A SIGNI can only have up to 1 [[Accessory]] attached to it. This craft is excluded from the game when the SIGNI it is attached to leaves the field.))\n\n" +
                "@C: If the SIGNI accessorized with this craft would leave the field, you may exclude this craft from the game instead. If you do, down that SIGNI."
        );

		setName("zh_simplified", "食用代号 温泉蛋");
        setDescription("zh_simplified", 
                "@>@C 被此牌附属的精灵离场的场合，作为替代，可以将此牌从游戏除外。这样做的场合，那只精灵横置。@@\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.COOKING);
        setLevel(3);
        setPower(13000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            ConstantAbility cont = registerConstantAbility(new TargetFilter().SIGNI().over(cardId), new RuleCheckModifier<>(CardRuleCheckType.ACTION_OVERRIDE, data ->
                new OverrideAction(GameEventId.MOVE, OverrideScope.CALLER, OverrideFlag.NON_MANDATORY, this::onConstEffModOverrideCond,this::onConstEffModOverrideHandler)
            ));
            cont.setActiveUnderFlags(GameConst.CardUnderType.ATTACHED_ACCESSORY);
        }
        
        private boolean onConstEffModOverrideCond(CardIndex cardIndex, GameEvent event, Ability sourceAbilityRC)
        {
            return !CardLocation.isSIGNI(((EventMove)event).getMoveLocation());
        }
        private void onConstEffModOverrideHandler(OverrideActionList list, Ability sourceAbilityRC)
        {
            list.addAction(new ActionExclude(getCardIndex()));
            list.addNonMandatoryAction(new ActionDown(list.getSourceEvent().getCallerCardIndex()));
        }
    }
}
