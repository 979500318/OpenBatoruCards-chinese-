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

public final class SPELL_B_EXCHANGE extends Card {
    
    public SPELL_B_EXCHANGE()
    {
        setImageSets("WXDi-P04-070");
        
        setOriginalName("EXCHANGE");
        setAltNames("エクスチェンジ Ekusucheinji");
        setDescription("jp",
                "対戦相手の手札を１枚見ないで選び、捨てさせる。その後、あなたのトラッシュからこの方法で捨てられたシグニと同じレベルの#Gを持たないシグニ１枚を対象とし、それを手札に加える。" +
                "~#：どちらか１つを選ぶ。\n" +
                "$$1カードを２枚引く。\n" +
                "$$2あなたのトラッシュから#Gを持たないシグニ１枚を対象とし、それを手札に加えるか場に出す。"
        );
        
        setName("en", "EXCHANGE");
        setDescription("en",
                "Your opponent discards a card at random. Then, add target SIGNI with the same level as the SIGNI discarded this way and without a #G from your trash to your hand." +
                "~#Choose one -- \n$$1 Draw two cards. \n$$2 Add target SIGNI without a #G from your trash to your hand or put it onto your field."
        );
        
        setName("en_fan", "EXCHANGE");
        setDescription("en_fan",
                "Choose 1 card from your opponent's hand without looking, and discard it. Then, target 1 SIGNI without #G @[Guard]@ with the same level as the SIGNI discarded this way from your trash, and add it to your hand." +
                "~#@[@|Choose 1 of the following:|@]@\n" +
                "$$1 Draw 2 cards.\n" +
                "$$2 Target 1 SIGNI without #G @[Guard]@ from your trash, and put it onto the field."
        );
        
		setName("zh_simplified", "EXCHANGE");
        setDescription("zh_simplified", 
                "不看对战对手的手牌选1张，舍弃。然后，从你的废弃区把与这个方法舍弃的精灵相同等级的不持有#G的精灵1张作为对象，将其加入手牌。" +
                "~#以下选1种。\n" +
                "$$1 抽2张牌。\n" +
                "$$2 从你的废弃区把不持有#G的精灵1张作为对象，将其加入手牌或出场。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1) + Cost.color(CardColor.BLACK, 1));
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            CardIndex cardIndex = playerChoiceHand().get();
            if(discard(cardIndex).get() != null)
            {
                int level = cardIndex.getIndexedInstance().getLevelByRef();
                
                if(CardType.isSIGNI(cardIndex.getCardReference().getType()))
                {
                    CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).withLevel(level).fromTrash()).get();
                    addToHand(target);
                }
            }
        }
        
        private void onLifeBurstEff()
        {
            if(playerChoiceMode() == 1)
            {
                draw(2);
            } else {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash()).get();
                putOnField(target);
            }
        }
    }
}
