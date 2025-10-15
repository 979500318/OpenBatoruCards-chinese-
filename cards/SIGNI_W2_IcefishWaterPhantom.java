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

public final class SIGNI_W2_IcefishWaterPhantom extends Card {
    
    public SIGNI_W2_IcefishWaterPhantom()
    {
        setImageSets("WXDi-P01-051");
        
        setOriginalName("幻水　シラウオ");
        setAltNames("ゲンスイシラウオ Gensui Shirauo");
        setDescription("jp",
                "@C：あなたの手札が４枚以上あるかぎり、このシグニのパワーは＋4000される。" +
                "~#：あなたのデッキの上からカードを３枚見る。その中からシグニを２枚まで公開し手札加え、残りを好きな順番でデッキの一番下に置く。"
        );
        
        setName("en", "Salangidae, Phantom Aquatic Beast");
        setDescription("en",
                "@C: As long as you have four or more cards in your hand, this SIGNI gets +4000 power." +
                "~#Look at the top three cards of your deck. Reveal up to two SIGNI from among them and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
        setName("en_fan", "Icefish, Water Phantom");
        setDescription("en_fan",
                "@C: As long as there are 4 or more cards in your hand, this SIGNI gets +4000 power." +
                "~#Look at the top 3 cards of your deck. Reveal up to 2 SIGNI from among them, and add them to your hand. Put the rest on the bottom of your deck in any order."
        );
        
		setName("zh_simplified", "幻水 白鱼");
        setDescription("zh_simplified", 
                "@C :你的手牌在4张以上时，这只精灵的力量+4000。" +
                "~#从你的牌组上面看3张牌。从中把精灵2张最多公开加入手牌，剩下的任意顺序放置到牌组最下面。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WATER_BEAST);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(4000));
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return getHandCount(getOwner()) >= 4 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            look(3);
            
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().fromLooked());
            reveal(data);
            addToHand(data);
            
            while(getLookedCount() > 0)
            {
                CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }
    }
}
