package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.UseCondition;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityShadow;
import open.batoru.game.FieldConst;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXFieldBackground;

public final class RESONA_W3_FullMoonWhiteNaturalStarPrincess extends Card {

    public RESONA_W3_FullMoonWhiteNaturalStarPrincess()
    {
        setImageSets("WXDi-P11-TK02");

        setOriginalName("白羅星姫　フルムーン");
        setAltNames("ハクラセイキフルムーン Hakuraseiki Furu Muun");
        setDescription("jp",
                "手札とエナゾーンからシグニを合計３枚トラッシュに置く\n\n" +
                "@C：対戦相手のターンの間、あなたの他のシグニは[[シャドウ（シグニ）]]を得る。\n" +
                "@E：次の対戦相手のターンの間、対戦相手はシグニアタックステップにシグニで合計一度しかアタックできない。"
        );

        setName("en", "Full Moon, White Planet Queen");
        setDescription("en",
                "Put three SIGNI from your hand and/or Ener Zone into your trash.\n\n" +
                "@C: During your opponent's turn, other SIGNI on your field gain [[Shadow -- SIGNI]]. \n" +
                "@E: During your opponent's next turn, your opponent can only attack with SIGNI once during the SIGNI attack step."
        );
        
        setName("en_fan", "Full Moon, White Natural Star Princess");
        setDescription("en_fan",
                "Put 3 SIGNI from your hand and/or ener zone into the trash\n\n" +
                "@C: During your opponent's turn, your other SIGNI gain [[Shadow (SIGNI)]].\n" +
                "@E: During your opponent's next turn, your opponent can only attack with a SIGNI once during the SIGNI attack step."
        );

		setName("zh_simplified", "白罗星姬 满月");
        setDescription("zh_simplified", 
                "[[出现条件]]主要阶段从手牌和能量区把精灵合计3张放置到废弃区\n" +
                "@C :对战对手的回合期间，你的其他的精灵得到[[暗影（精灵）]]。\n" +
                "@E :下一个对战对手的回合期间，对战对手在精灵攻击步骤，精灵只能合计一次攻击。\n"
        );

        setCardFlags(CardFlag.CRAFT);

        setType(CardType.RESONA);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(3);
        setPower(12000);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            setUseCondition(UseCondition.RESONA, 3, new TargetFilter().or(new TargetFilter().fromHand(), new TargetFilter().fromEner()));

            registerConstantAbility(this::onConstEffCond, new TargetFilter().own().SIGNI().except(cardId), new AbilityGainModifier(this::onConstEffModGetSample));

            registerEnterAbility(this::onEnterEff);
        }

        private ConditionState onConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) ? ConditionState.OK : ConditionState.BAD;
        }

        private void onEnterEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_ATTACK, TargetFilter.HINT_OWNER_OP, this::onAttachedConstEffModRuleCheck));
            attachedConst.setCondition(this::onConstEffCond);
            
            GFX.attachToAbility(attachedConst, new GFXFieldBackground(getOpponent(), "full_moon", 800,800, FieldConst.FIELD_CARD_HEIGHT + FieldConst.FIELD_ZONE_HSPACING,10+FieldConst.FIELD_ZONE_VSPACING/2, new int[]{150,150,150}));
            
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
        private RuleCheckState onAttachedConstEffModRuleCheck(RuleCheckData data)
        {
            return CardType.isSIGNI(data.getSourceCardIndex().getCardReference().getType()) &&
                    GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.ATTACK && CardType.isSIGNI(event.getCaller().getCardReference().getType())) > 0 ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
    }
}

