package open.batoru.data.cards;

import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.ChronoRecordScheduler.ChronoRecord;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ChoiceLogic;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.game._3d.Group3D;
import open.batoru.game.animations.AnimationSpinnerRotateSingle;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneSpinner;
import open.batoru.game.gfx.GFXZoneSpinner.GFXFlatSpinnerObject;

public final class ARTS_BK_BluPurFrustration extends Card {

    public ARTS_BK_BluPurFrustration()
    {
        setImageSets("WX24-P4-007","WX24-P4-007U");

        setOriginalName("ブッパ・フラストレーション");
        setAltNames("ブッパ・フラストレーション Buppu Furasutoreeshon");
        setDescription("jp",
                "以下の３つを行う。\n" +
                "$$1対戦相手は自分のシグニ１体を場からトラッシュに置かないかぎり、手札を３枚捨てる。\n" +
                "$$2対戦相手は手札を３枚捨てないかぎり、自分のシグニ１体を選びトラッシュに置く。\n" +
                "$$3次の対戦相手のメインフェイズとアタックフェイズの間、対戦相手のトラッシュにあるカードは対戦相手の効果によって他の領域に移動しない。"
        );

        setName("en", "BluPur Frustration");
        setDescription("en",
                "@[@|Do the following 3:|@]@\n" +
                "$$1 Your opponent discards 3 cards from their hand unless they put 1 of their SIGNI from the field into the trash.\n" +
                "$$2 Your opponent chooses 1 of their SIGNI on the field and puts it into the trash unless they discard 3 cards from their hand.\n" +
                "$$3 During your opponent's next main phase and attack phase, cards in your opponent's trash can't be moved to other zones by your opponent's effects."
        );

		setName("zh_simplified", "痛悔·前非");
        setDescription("zh_simplified", 
                "进行以下的3种。\n" +
                "$$1 对战对手如果不把自己的精灵1只从场上放置到废弃区，那么把手牌3张舍弃。\n" +
                "$$2 对战对手如果不把手牌3张舍弃，那么选自己的精灵1只放置到废弃区。\n" +
                "$$3 下一个对战对手的主要阶段和攻击阶段期间，对战对手的废弃区的牌不会因为对战对手的效果往其他的领域移动。\n"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLUE, CardColor.BLACK);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.color(CardColor.BLACK, 1));
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

            registerARTSAbility(this::onARTSEff);
        }

        private void onARTSEff()
        {
            payMandatory(getOpponent(), new DiscardCost(() -> Math.min(3, getHandCount(getOpponent()))), new TrashCost(new TargetFilter().SIGNI()));
            
            payMandatory(getOpponent(), new TrashCost(() -> getSIGNICount(getOpponent()) >= 1 ? 1 : 0, new TargetFilter().SIGNI()), new DiscardCost(3));

            int currentTurn = getTurnCount();
            ChronoRecord record = new ChronoRecord(ChronoDuration.nextTurnEnd(getOpponent()));
            ConstantAbilityShared attachedConstShared = new ConstantAbilityShared(new TargetFilter().OP().fromTrash(), new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_MOVED, data -> {
                if(getTurnCount() != currentTurn &&
                  (getCurrentPhase() == GamePhase.MAIN || GamePhase.isAttackPhase(getCurrentPhase())) &&
                   data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex())) return RuleCheckState.BLOCK;
                
                return RuleCheckState.IGNORE;
            }));
            GFX.attachToChronoRecord(record, new GFXZoneSpinner(getOpponent(), CardLocation.TRASH, new AnimationSpinnerRotateSingle(8000, -10), new Group3D[]{new GFXFlatSpinnerObject("zones/chain_circle")}));
            attachPlayerAbility(getOwner(), attachedConstShared, record);
        }
    }
}
