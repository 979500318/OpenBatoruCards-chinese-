package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.RuleCheckData;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.core.gameplay.rulechecks.card.RuleCheckCanBeMoved;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.CardLRIGTeam;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.PieceAbility;
import open.batoru.data.ability.modifiers.ModifiableValueModifier;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game.FieldConst;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXFieldBackground;

public final class PIECE_RWB_DALIVE extends Card {

    public PIECE_RWB_DALIVE()
    {
        setImageSets("WXDi-P16-002");

        setOriginalName("D-(A)LIVE!!");
        setAltNames("ディーアライブ Dii A Raibu D-Alive DALIVE");
        setDescription("jp",
                "=U =T ＜夢限少女＞＆全員レベル１以上\n\n" +
                "次の対戦相手のターンの間、あなたは対戦相手のルリグによってダメージを受けず、グロウフェイズ以外であなたのデッキと手札とエナゾーンにあるカードは対戦相手の効果によってトラッシュに移動しない。\n" +
                "次の対戦相手のターン終了時、カードを１枚引き【エナチャージ１】をする。\n" +
                "あなたのセンタールリグがレベル２以上の場合、次のあなたのエナフェイズ終了時まで、あなたのセンタールリグのリミットを＋２する。"
        );

        setName("en", "D - LIVE!!");
        setDescription("en",
                "=U You have =T <<MUGEN SHOJO>> on your field with all members level one or more.\n\nDuring your opponent's next turn, you do not take damage from your opponent's LRIG, and cards in your deck, hand, and Ener Zone cannot be moved into your trash by your opponent's effects outside of the grow phase.\nAt the beginning of your opponent's next end phase, draw a card and [[Ener Charge 1]].\nIf your Center LRIG is level two or more, increase your Center LRIG's limit by two until the end of your next Ener phase."
        );
        
        setName("en_fan", "D-(A)LIVE!!");
        setDescription("en_fan",
                "=U =T <<Mugen Shoujo>> and all of them are level 1 or higher\n\n" +
                "During your opponent's next turn, you don't take damage from your opponent's LRIG, and cards in your deck, hand, and ener zone cannot be put into the trash by your opponent's effects other than during the grow phase.\n" +
                "At the end of your opponent's next turn, draw 1 card and [[Ener Charge 1]].\n" +
                "If your center LRIG is level 2 or higher, until the end of your next ener phase, your center LRIG gets +2 limit."
        );

		setName("zh_simplified", "D-(A)LIVE!!");
        setDescription("zh_simplified", 
                "=U=T<<夢限少女>>＆全员等级1以上\n" +
                "下一个对战对手的回合期间，你不会因为对战对手的分身受到伤害，在成长阶段以外你的牌组和手牌和能量区的牌不会因为对战对手的效果往废弃区移动。\n" +
                "下一个对战对手的回合结束时，抽1张牌并[[能量填充1]]。\n" +
                "你的核心分身在等级2以上的场合，直到下一个你的充能阶段结束时为止，你的核心分身的界限+2。\n"
        );

        setType(CardType.PIECE);
        setLRIGTeam(CardLRIGTeam.MUGEN_SHOUJO);
        setColor(CardColor.RED, CardColor.BLUE, CardColor.WHITE);
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            PieceAbility piece = registerPieceAbility(this::onPieceEff);
            piece.setCondition(this::onPieceEffCond);
        }

        private ConditionState onPieceEffCond()
        {
            return new TargetFilter().own().anyLRIG().withLevel(1,0).getValidTargetsCount() == 3 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onPieceEff()
        {
            ChronoRecord record = new ChronoRecord(ChronoDuration.nextTurnEnd(getOpponent()));
            GFX.attachToChronoRecord(record, new GFXFieldBackground(getOwner(), "spotlights", 2686,948, FieldConst.FIELD_CARD_WIDTH*2 + FieldConst.FIELD_ZONE_HSPACING+10,FieldConst.FIELD_ZONE_VSPACING+100).withCenterOffset());
            
            addPlayerRuleCheck(PlayerRuleCheckType.CAN_BE_DAMAGED, getOwner(), record, data ->
                !isOwnTurn() && !isOwnCard(data.getSourceCardIndex()) && CardType.isLRIG(data.getSourceCardIndex().getCardReference().getType()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE
            );
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().own().fromLocation(CardLocation.DECK_MAIN, CardLocation.HAND, CardLocation.ENER),
                new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_MOVED, this::onAttachedConstEff1ModRuleCheck)
            );
            attachedConst.setCondition(this::onAttachedConstEff1Cond);

            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));

            callDelayedEffect(ChronoDuration.nextTurnEnd(getOpponent()), () -> {
                draw(1);
                enerCharge(1);
            });

            if(getLRIG(getOwner()).getIndexedInstance().getLevel().getValue() >= 2)
            {
                ConstantAbilityShared attachedConst2 = new ConstantAbilityShared(new TargetFilter().own().LRIG(), new ModifiableValueModifier<>(this::onAttachedConstEff2ModGetSample, () -> 2d));
                attachPlayerAbility(getOwner(), attachedConst2, ChronoDuration.nextPhaseEnd(getOwner(), GamePhase.ENER));
            }
        }
        private ConditionState onAttachedConstEff1Cond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private RuleCheckState onAttachedConstEff1ModRuleCheck(RuleCheckData data)
        {
            return !isOwnTurn() && getCurrentPhase() != GamePhase.GROW && RuleCheckCanBeMoved.getDataTargetLocation(data) == CardLocation.TRASH && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
        }
        private ModifiableDouble onAttachedConstEff2ModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getLimit();
        }
    }
}


