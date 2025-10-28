package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.CardConst.UseTiming;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.events.EventPowerChanged;
import open.batoru.data.ability.modifiers.ModifiableAddedValueModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class ARTS_K_DarkWhip extends Card {

    public ARTS_K_DarkWhip()
    {
        setImageSets("WX24-D5-05");

        setOriginalName("ダーク・ウィップ");
        setAltNames("ダークウィップ Daaku Uippu");
        setDescription("jp",
                "このターン、あなたの効果によって対戦相手のシグニのパワーが－（マイナス）される場合、代わりに２倍－（マイナス）される。\n" +
                "このターン、対戦相手のシグニ１体のパワーが０以下になったとき、対戦相手のデッキの上からカードを２枚トラッシュに置く。"
        );

        setName("en", "Dark Whip");
        setDescription("en",
                "This turn, if the power of your opponent's SIGNI would be -- (minus) by an effect, it gets -- (minus) by twice as much instead.\n" +
                "This turn, whenever the power of your opponent's SIGNI becomes 0 or less, put the top 2 cards of your opponent's deck into the trash."
        );

        setName("zh_simplified", "黑暗·之鞭");
        setDescription("zh_simplified", 
                "这个回合，因为你的效果把对战对手的精灵的力量-（减号）的场合，作为替代，2倍-（减号）。\n" +
                "这个回合，当对战对手的精灵1只的力量在0以下时，从对战对手的牌组上面把2张牌放置到废弃区。"
        );

        setType(CardType.ARTS);
        setColor(CardColor.BLACK);
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
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                new ModifiableAddedValueModifier<>(this::onAttachedConstEffModGetSample, this::onAttachedConstEffModAddedValue)
            );
            GFXCardTextureLayer.attachToSharedAbility(attachedConst, cardIndex -> new GFXCardTextureLayer(cardIndex, "double_minus"));
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            
            AutoAbility attachedAuto = new AutoAbility(GameEventId.POWER_CHANGED, this::onAttachedAutoEff);
            attachedAuto.setCondition(this::onAttachedAutoEffCond);
            attachPlayerAbility(getOwner(), attachedAuto, ChronoDuration.turnEnd());
        }
        private ModifiableDouble onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getPower();
        }
        private double onAttachedConstEffModAddedValue(ModifiableDouble mod, double addedValue)
        {
            return addedValue < 0 ? addedValue * 2 : addedValue;
        }
        private ConditionState onAttachedAutoEffCond(CardIndex cardIndex)
        {
            return !isOwnCard(cardIndex) && EventPowerChanged.getDataNewValue() <= 0 ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAttachedAutoEff(CardIndex cardIndex)
        {
            millDeck(getOpponent(), 2);
        }
    }
}

