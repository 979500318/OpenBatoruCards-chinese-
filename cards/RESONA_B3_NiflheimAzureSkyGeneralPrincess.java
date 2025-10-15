package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleValueType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AbilityConst.ActionHint;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.events.EventMove;
import open.batoru.data.ability.modifiers.PlayerRuleBaseValueModifier;

public final class RESONA_B3_NiflheimAzureSkyGeneralPrincess extends Card {

    public RESONA_B3_NiflheimAzureSkyGeneralPrincess()
    {
        setImageSets("WX25-P2-TK05");

        setOriginalName("蒼穹将姫　ニブルヘイム");
        setAltNames("ソウキュウショウキニブルヘイム Soukyuu Shouki Niburuheimu");
        setDescription("jp",
                "手札とエナゾーンからシグニを合計２枚トラッシュに置く\n\n" +
                "@C：対戦相手はドローフェイズの間にカードを合計１枚までしか引けない。\n" +
                "@U：このシグニが場を離れたとき、カードを２枚引くか、対戦相手は手札を２枚捨てる。"
        );

        setName("en", "Niflheim, Azure Sky General Princess");
        setDescription("en",
                "Put 2 SIGNI from your hand and/or ener zone into the trash\n\n" +
                "@C: Your opponent can only draw up to 1 card in total during their draw phase.\n" +
                "@U: When this SIGNI leaves the field, draw 2 cards or your opponent discards 2 cards from their hand."
        );

		setName("zh_simplified", "苍穹将姬 尼福尔海姆");
        setDescription("zh_simplified", 
                "[[出现条件]]主要阶段从手牌和能量区把精灵合计2张放置到废弃区\n" +
                "@C :对战对手的抽牌阶段期间，只能抽合计1张最多的牌。\n" +
                "@U :当这只精灵离场时，抽2张牌或，对战对手把手牌2张舍弃。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.RESONA);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(3);
        setPower(12000);
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

            setUseCondition(UseCondition.RESONA, 2, new TargetFilter().or(new TargetFilter().fromHand(), new TargetFilter().fromEner()));

            registerConstantAbility(new PlayerRuleBaseValueModifier(PlayerRuleValueType.DRAW_PHASE_MAX, TargetFilter.HINT_OWNER_OP, 1));
            
            AutoAbility auto = registerAutoAbility(GameEventId.MOVE, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
        }

        private ConditionState onAutoEffCond()
        {
            return !CardLocation.isSIGNI(EventMove.getDataMoveLocation()) ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff()
        {
            if(playerChoiceAction(ActionHint.DRAW, ActionHint.DISCARD) == 1)
            {
                draw(2);
            } else {
                discard(getOpponent(), 2);
            }
        }
    }
}

