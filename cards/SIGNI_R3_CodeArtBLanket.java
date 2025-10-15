package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_R3_CodeArtBLanket extends Card {
    
    public SIGNI_R3_CodeArtBLanket()
    {
        setImageSets("WXDi-P03-061");
        
        setOriginalName("コードアート　Ｂランケット");
        setAltNames("コードアートビーランケット Koodo Aato Bii Ranketto Blanket");
        setDescription("jp",
                "@C：あなたのトラッシュにスペルがあるかぎり、このシグニのパワーは＋3000される。\n" +
                "@E：あなたのデッキの上からカードを５枚見る。その中からスペルを好きな枚数公開し手札に加え、残りを好きな順番でデッキの一番下に置く。この方法で手札に加えたカード１枚につき手札を１枚捨てる。" +
                "~#：あなたのトラッシュからスペルを２枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "B-Lanket, Code: Art");
        setDescription("en",
                "@C: As long as there is a spell in your trash, this SIGNI gets +3000 power.\n" +
                "@E: Look at the top five cards of your deck. Reveal any number of spells from among them and add them to your hand. Put the rest on the bottom of your deck in any order. Discard a card for each card added to your hand this way." +
                "~#Add up to two target spells from your trash to your hand."
        );
        
        setName("en_fan", "Code Art B Lanket");
        setDescription("en_fan",
                "@C: As long as there is a spell in your trash, this SIGNI gets +3000 power.\n" +
                "@E: Look at the top 5 cards of your deck. Reveal any number of spells from among them, add them to your hand, and put the rest on the bottom of your deck in any order. Discard 1 card from your hand for each spell added to your hand this way." +
                "~#Target up to 2 spells from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "必杀代号 电热毯");
        setDescription("zh_simplified", 
                "@C :你的废弃区有魔法时，这只精灵的力量+3000。\n" +
                "@E :从你的牌组上面看5张牌。从中把魔法任意张数公开加入手牌，剩下的任意顺序放置到牌组最下面。这个方法加入手牌的牌的数量，每有1张就把手牌1张舍弃。" +
                "~#从你的废弃区把魔法2张最多作为对象，将这些加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.ELECTRIC_MACHINE);
        setLevel(3);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(3000));
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().spell().fromTrash().getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onEnterEff()
        {
            look(5);
            
            DataTable<CardIndex> data = playerTargetCard(0,5, new TargetFilter(TargetHint.HAND).own().spell().fromLooked());
            reveal(data);
            int countAdded = addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
            
            discard(countAdded);
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().spell().fromTrash());
            addToHand(data);
        }
    }
}
