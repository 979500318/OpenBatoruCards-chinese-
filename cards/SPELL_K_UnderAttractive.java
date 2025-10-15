package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ModifiableDouble;
import open.batoru.data.ability.ConstantAbilityShared;
import open.batoru.data.ability.modifiers.ConstantModifier;
import open.batoru.data.ability.modifiers.ModifiableAddedValueModifier;
import open.batoru.data.ability.modifiers.PowerModifier;
import open.batoru.game.gfx.GFXCardTextureLayer;

public final class SPELL_K_UnderAttractive extends Card {
    
    public SPELL_K_UnderAttractive()
    {
        setImageSets("WXDi-P01-088");
        
        setOriginalName("アンダー・アトラクティブ");
        setAltNames("アンダーアトラクティブ Andaa Atorakutibu");
        setDescription("jp",
                "このターン、あなたのシグニの効果によって対戦相手のシグニのパワーが－（マイナス）される場合、代わりに２倍－（マイナス）される。\n" +
                "あなたのトラッシュにカードが２５枚以上ある場合、あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、ターン終了時まで、それのパワーを－15000する。"
        );
        
        setName("en", "Under Attractive");
        setDescription("en",
                "If an effect of your SIGNI would -- (minus) the power of a SIGNI on your opponent's field this turn, the amount of power that would be -- (minused) is doubled instead.\n" +
                "If you have twenty five or more cards in your trash, add target SIGNI without a #G from your trash to your hand." +
                "~#Target upped SIGNI on your opponent's field gets --15000 power until end of turn."
        );
        
        setName("en_fan", "Under Attractive");
        setDescription("en_fan",
                "This turn, if the power of your opponent's SIGNI would be -- (minus) by the effect of your SIGNI, it gets -- (minus) by twice as much instead.\n" +
                "If there are 25 or more cards in your trash, target 1 SIGNI without #G @[Guard]@ from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's upped SIGNI, and until end of turn, it gets --15000 power."
        );
        
		setName("zh_simplified", "暗流·涌动");
        setDescription("zh_simplified", 
                "这个回合，因为你的精灵的效果把对战对手的精灵的力量-（减号）的场合，作为替代，2倍-（减号）。\n" +
                "你的废弃区的牌在25张以上的场合，从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，直到回合结束时为止，其的力量-15000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerSpellAbility(this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEff()
        {
            ConstantAbilityShared attachedConst = new ConstantAbilityShared(new TargetFilter().OP().SIGNI(),
                new ModifiableAddedValueModifier<>(this::onAttachedConstEffModGetSample, this::onAttachedConstEffModAddedValue)
            );
            GFXCardTextureLayer.attachToSharedAbility(attachedConst, cardIndex -> new GFXCardTextureLayer(cardIndex, "double_minus"));
            attachedConst.setForcedModifierUpdate(mod -> mod instanceof PowerModifier);
            attachPlayerAbility(getOwner(), attachedConst, ChronoDuration.turnEnd());
            
            if(getTrashCount(getOwner()) >= 25)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
                addToHand(target);
            }
        }
        private ModifiableDouble onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().getPower();
        }
        private double onAttachedConstEffModAddedValue(ModifiableDouble mod, double addedValue)
        {
            if(addedValue < 0 && isOwnCard(mod.getSourceAbility().getSourceCardIndex()) &&
               CardType.isSIGNI(mod.getSourceAbility().getSourceCardIndex().getCardReference().getType()))
            {
                return addedValue * 2;
            }
            return addedValue;
        }
        
        private void onLifeBurstEff()
        {
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI().upped()).get();
            gainPower(cardIndex, -15000, ChronoDuration.turnEnd());
        }
    }
}
