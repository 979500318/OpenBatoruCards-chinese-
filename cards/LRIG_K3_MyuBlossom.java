package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.SIGNIZonePosition;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.CostRuleCheck;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardLRIGType;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.UseLimit;
import open.batoru.data.ability.ActionAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.FieldZone;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class LRIG_K3_MyuBlossom extends Card {

    public LRIG_K3_MyuBlossom()
    {
        setImageSets("WXDi-P11-009", "WXDi-P11-009U");

        setOriginalName("ミュウ＝ブロッサム");
        setAltNames("ミュウブロッサム Myuu Burossamu");
        setDescription("jp",
                "@C：【チャーム】が付いている対戦相手のシグニのパワーを－3000する。\n" +
                "@A $T1 %K0：対戦相手のシグニ１体を対象とし、対戦相手のトラッシュからカード１枚をそれの【チャーム】にする。\n" +
                "@A $G1 %K0：シグニのない対戦相手のシグニゾーン１つを指定する。このターンと次のターンの間、対戦相手は%X %X %X %X %Xを支払わないかぎり指定されたシグニゾーンにシグニを新たに配置できない。"
        );

        setName("en", "Myu=Blossom");
        setDescription("en",
                "@C: SIGNI on your opponent's field with a [[Charm]] attached get --3000 power.\n" +
                "@A $T1 %K0: Attach a card from your opponent's trash to target SIGNI on your opponent's field as a [[Charm]].\n" +
                "@A $G1 %K0: Choose one of your opponent's SIGNI Zones where there is no SIGNI. Your opponent cannot put SIGNI into that SIGNI Zone during this turn and the next turn unless they pay %X %X %X %X %X."
        );
        
        setName("en_fan", "Myu-Blossom");
        setDescription("en_fan",
                "@C: All of your opponent's SIGNI with [[Charm]] attached to them get --3000 power.\n" +
                "@A $T1 %K0: Target 1 of your opponent's SIGNI, and attach 1 card from your opponent's trash to it as a [[Charm]].\n" +
                "@A $G1 %K0: Choose 1 of your opponent's unoccupied SIGNI zones. During this turn and the next turn, your opponent can't place SIGNI onto that SIGNI zone unless they pay %X %X %X %X %X."
        );

		setName("zh_simplified", "缪=绽放");
        setDescription("zh_simplified", 
                "@C :有[[魅饰]]附加的对战对手的精灵的力量-3000。\n" +
                "@A $T1 %K0:对战对手的精灵1只作为对象，从对战对手的废弃区把1张牌作为其的[[魅饰]]。\n" +
                "@A $G1 %K0没有精灵的对战对手的精灵区1个指定。这个回合和下一个回合期间，对战对手如果不把%X %X %X %X %X:支付，那么不能在指定的精灵区把精灵新配置。\n"
        );

        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.MYU);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 2));
        setLevel(3);
        setLimit(6);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerConstantAbility(new TargetFilter().OP().SIGNI().withCardsUnder(CardUnderType.ATTACHED_CHARM), new PowerModifier(-3000));

            ActionAbility act1 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff1);
            act1.setUseLimit(UseLimit.TURN, 1);

            ActionAbility act2 = registerActionAbility(new EnerCost(Cost.color(CardColor.BLACK, 0)), this::onActionEff2);
            act2.setUseLimit(UseLimit.GAME, 1);
        }

        private void onActionEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ATTACH).OP().SIGNI().attachable(CardUnderType.ATTACHED_CHARM)).get();

            if(target != null)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.ATTACH).OP().fromTrash()).get();
                attach(target, cardIndex, CardUnderType.ATTACHED_CHARM);
            }
        }
        
        private void onActionEff2()
        {
            FieldZone fieldZone = playerTargetZone(new TargetFilter().OP().SIGNI().unoccupied()).get();
            
            if(fieldZone != null)
            {
                ChronoRecord record = new ChronoRecord(ChronoDuration.turnEnd().repeat(2));
                GFX.attachToChronoRecord(record, new GFXZoneUnderIndicator(getOpponent(),fieldZone.getZoneLocation(), "web"));
                
                addPlayerRuleCheck(PlayerRuleCheckType.COST_TO_PLACE_SIGNI_ON_ZONE, getOpponent(), record, data ->
                    !isOwnCard(CostRuleCheck.getCardIndex(data)) &&
                    data.getGenericData(1) == SIGNIZonePosition.getSIGNIPositionByCardLocation(fieldZone.getZoneLocation())
                     ? new EnerCost(Cost.colorless(5)) : null
                );
            }
        }
    }
}
