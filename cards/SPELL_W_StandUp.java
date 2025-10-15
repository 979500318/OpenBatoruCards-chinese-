package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;
import open.batoru.data.ability.SpellAbility;

public final class SPELL_W_StandUp extends Card {
    
    public SPELL_W_StandUp()
    {
        setImageSets("WXDi-P01-052");
        
        setOriginalName("スタンド・アップ");
        setAltNames("スタンドアップ Sutando Appu");
        setDescription("jp",
                "あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のアップ状態のシグニ１体を対象とし、それを手札に戻す。"
        );
        
        setName("en", "Stand Up");
        setDescription("en",
                "Add target SIGNI with a #G from your trash to your hand." +
                "~#Return target upped SIGNI on your opponent's field to its owner's hand."
        );
        
        setName("en_fan", "Stand Up");
        setDescription("en_fan",
                "Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's upped SIGNI, and return it to their hand."
        );
        
		setName("zh_simplified", "起来·竖直");
        setDescription("zh_simplified", 
                "从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的竖直状态的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.WHITE);
        setCost(Cost.color(CardColor.WHITE, 2));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final SpellAbility spell;
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            spell = registerSpellAbility(this::onSpellEffPreTarget, this::onSpellEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onSpellEffPreTarget()
        {
            spell.setTargets(playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()));
        }
        private void onSpellEff()
        {
            addToHand(spell.getTarget());
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().upped()).get();
            addToHand(target);
        }
    }
}
