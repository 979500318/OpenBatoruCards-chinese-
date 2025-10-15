package open.batoru.data.cards;

import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DiscardCost;

public final class LRIGA_R1_ExEcho extends Card {
    
    public LRIGA_R1_ExEcho()
    {
        setImageSets("WXDi-P04-018");
        
        setOriginalName("エクスエコー");
        setAltNames("Ekusu Ekoo");
        setDescription("jp",
                "@E @[手札から赤のカードを１枚捨てる]@：カードを３枚引く。\n" +
                "@U：このカードがエクシードのコストとしてルリグトラッシュに置かれたとき、手札を１枚捨ててもよい。そうした場合、カードを１枚引く。"
        );
        
        setName("en", "Ex Echo");
        setDescription("en",
                "@E @[Discard a red card]@: Draw three cards.\n\n" +
                "@U: When this card is put into the LRIG Trash as an Exceed cost, you may discard a card. If you do, draw a card."
        );
        
        setName("en_fan", "Ex Echo");
        setDescription("en_fan",
                "@E @[Discard 1 red card from your hand]@: Draw 3 cards.\n" +
                "@U: When this card is put into your LRIG trash for an @[Exceed]@ cost, you may discard 1 card from your hand. If you do, draw 1 card."
        );
        
		setName("zh_simplified", "艾克斯回声");
        setDescription("zh_simplified", 
                "@E 从手牌把红色的牌1张舍弃:抽3张牌。\n" +
                "@U :当这张牌作为超越的费用放置到分身废弃区时，可以把手牌1张舍弃。这样做的场合，抽1张牌。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.EX);
        setLRIGTeam(CardLRIGTeam.DEUS_EX_MACHINA);
        setColor(CardColor.RED);
        setLevel(1);
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
            
            registerEnterAbility(new DiscardCost(new TargetFilter().withColor(CardColor.RED)), this::onEnterEff);
            
            registerAutoAbility(GameEventId.EXCEED, this::onAutoEff);
        }
        
        private void onEnterEff()
        {
            draw(3);
        }
        
        private void onAutoEff()
        {
            if(discard(0,1).get() != null)
            {
                draw(1);
            }
        }
    }
}
