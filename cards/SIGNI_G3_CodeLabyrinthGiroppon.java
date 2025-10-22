package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.SIGNIZonePosition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.SIGNIZoneRuleCheck.SIGNIZonePositionGroup;
import open.batoru.core.gameplay.rulechecks.SIGNIZoneRuleCheck.SIGNIZonePositionGroup.SIGNIZonePositionState;
import open.batoru.core.gameplay.rulechecks.card.RuleCheckCanBeMoved;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry;
import open.batoru.core.gameplay.rulechecks.SIGNIZoneRuleCheck;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class SIGNI_G3_CodeLabyrinthGiroppon extends Card {

    public SIGNI_G3_CodeLabyrinthGiroppon()
    {
        setImageSets("WXDi-P03-043", "SPDi02-24");

        setOriginalName("コードラビリンス　ギロッポン");
        setAltNames("コードラビリンスギロッポン Koodo Rabirinsu Giroppon");
        setDescription("jp",
                "@C：対戦相手がシグニを配置する場合、可能ならばこのシグニの正面に配置しなければならない。\n" +
                "@C：あなたのターンの間、このシグニは対戦相手の効果によって場からエナゾーン以外の領域に移動しない。\n" +
                "@U：対戦相手のシグニ１体がこのシグニの正面に配置されたとき、ターン終了時まで、それのパワーを－3000してもよい。"
        );

        setName("en", "Giroppon, Code: Labyrinth");
        setDescription("en",
                "@C: As your opponent places a SIGNI on their field, they must place it in front of this SIGNI if possible.\n" +
                "@C: During your turn, this SIGNI cannot be moved from the field to anywhere other than the Ener Zone by your opponent's effects.\n" +
                "@U: Whenever a SIGNI on your opponent's field is placed in front of this SIGNI, you may give that SIGNI --3000 power until end of turn."
        );

        setName("en_fan", "Code Labyrinth Giroppon");
        setDescription("en_fan",
                "@C: If your opponent would place a SIGNI, it must be placed in front of this SIGNI if able.\n" +
                "@C: During your turn, this SIGNI can't be moved from the field to zones other than the ener zone by your opponent's effects.\n" +
                "@U: Whenever 1 of your opponent's SIGNI is placed in front of this SIGNI, you may have that SIGNI get --3000 power until end of turn."
        );

		setName("zh_simplified", "迷牢代号 六本木");
        setDescription("zh_simplified", 
                "@C :对战对手把精灵配置的场合，如果能在这只精灵的正面配置，则必须在这只精灵的正面配置。\n" +
                "@C :你的回合期间，这只精灵不会因为对战对手的效果从场上往能量区以外的领域移动。\n" +
                "@U :当对战对手的精灵1只在这只精灵的正面配置时，直到回合结束时为止，可以将其的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.LABYRINTH);
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

            registerConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckRegistry.PlayerRuleCheckType.CAN_PLACE_SIGNI_ON_ZONE,
                TargetFilter.HINT_OWNER_OP, this::onConstEff1ModRuleCheck)
            );

            registerConstantAbility(this::onConstEff2Cond, new RuleCheckModifier<>(CardRuleCheckRegistry.CardRuleCheckType.CAN_BE_MOVED, this::onConstEff2ModRuleCheck));

            AutoAbility auto = registerAutoAbility(GameEventId.PLACE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private SIGNIZonePositionGroup onConstEff1ModRuleCheck(RuleCheckData data)
        {
            SIGNIZonePosition zonePositionOP = SIGNIZonePosition.getSIGNIPositionByCardLocation(getCardIndex().getLocation());
            if(zonePositionOP != null)
            {
                SIGNIZonePosition zonePosition = SIGNIZonePosition.getOppositeSIGNIPosition(zonePositionOP);
                if(zonePosition != null && SIGNIZoneRuleCheck.getDataZoneSIGNIPosition(data) != zonePosition && getOppositeSIGNI() == null)
                {
                    SIGNIZonePositionGroup group = new SIGNIZonePositionGroup(SIGNIZonePositionState.DENY);
                    group.setState(zonePosition, SIGNIZonePositionState.ALLOW);
                    return group;
                }
            }
            return new SIGNIZonePositionGroup(SIGNIZonePositionState.IGNORE);
        }

        private ConditionState onConstEff2Cond()
        {
            return isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onConstEff2ModRuleCheck(RuleCheckData data)
        {
            return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) &&
                    !CardLocation.isSIGNI(RuleCheckCanBeMoved.getDataTargetLocation(data)) &&
                    RuleCheckCanBeMoved.getDataTargetLocation(data) != CardLocation.ENER ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }

        private ConditionState onAutoEffCond(CardIndex caller)
        {
            return !isOwnCard(caller) && caller.getLocation() == CardLocation.getOppositeSIGNILocation(getCardIndex().getLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(playerChoiceActivate())
            {
                gainPower(caller, -3000, ChronoDuration.turnEnd());
            }
        }
    }
}
