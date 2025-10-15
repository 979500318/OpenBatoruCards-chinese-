package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W1_DeusTHEDOORSmallEquipment extends Card {

    public SIGNI_W1_DeusTHEDOORSmallEquipment()
    {
        setImageSets("WXDi-P16-059");

        setOriginalName("小装　デウス//THE DOOR");
        setAltNames("ショウソウデウスザドアー Shousou Deusu Za Doaa");
        setDescription("jp",
                "@C：このシグニは同じシグニゾーンに【ゲート】があるかぎり、@>@C：対戦相手は追加で%Xを支払わないかぎり【ガード】ができない。@@を得る。\n" +
                "@U：あなたのターン終了時、あなたの場に【ゲート】がある場合、あなたのシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは[[シャドウ（レベル２以下）]]を得る。"
        );

        setName("en", "Deus//THE DOOR, Lightly Armed");
        setDescription("en",
                "@C: As long as this SIGNI is in the same SIGNI Zone as a [[Gate]], it gains@>@C: Your opponent cannot [[Guard]] unless they pay an additional %X.@@@U: At the end of your turn, if there is a [[Gate]] on your field, target SIGNI on your field gains [[Shadow -- Level two or less]] until the end of your opponent's next end phase. "
        );
        
        setName("en_fan", "Deus//THE DOOR, Small Equipment");
        setDescription("en_fan",
                "@C: As long as this SIGNI is on a SIGNI zone with a [[Gate]], it gains:" +
                "@>@C: Your opponent can't [[Guard]] unless they pay %X.@@" +
                "@U: At the end of your turn, if there is a [[Gate]] on your field, target 1 of your SIGNI, and until the end of your opponent's next turn, it gains [[Shadow (level 2 or lower)]]."
        );

		setName("zh_simplified", "小装 迪乌斯//THE DOOR");
        setDescription("zh_simplified", 
                "@C :这只精灵的相同精灵区有[[大门]]时，得到\n" +
                "@>@C 对战对手如果不追加把%X:支付，那么不能[[防御]]。@@\n" +
                "@U :你的回合结束时，你的场上有[[大门]]的场合，你的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到[[暗影（等级2以下）]]。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEFENSE_FACTION,CardSIGNIClass.ARM);
        setLevel(1);
        setPower(3000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(this::onConstEffCond, new AbilityGainModifier(this::onConstEffModGetSample));

            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onConstEffCond()
        {
            return hasZoneObject(CardUnderType.ZONE_GATE) ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerConstantAbility(this::onConstEffCond, new PlayerRuleCheckModifier<>(PlayerRuleCheckType.COST_TO_GUARD,
                TargetFilter.HINT_OWNER_OP, data -> new EnerCost(Cost.colorless(1))
            ));
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            if(new TargetFilter().own().SIGNI().zone().withZoneObject(CardUnderType.ZONE_GATE).getValidTargetsCount() > 0)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
                if(target != null) attachAbility(target, new StockAbilityShadow(this::onAttachedStockEffAddCond), ChronoDuration.nextTurnEnd(getOpponent()));
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return (CardType.isSIGNI(cardIndexSource.getCardReference().getType()) || CardType.isLRIG(cardIndexSource.getCardReference().getType())) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
    }
}
