package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class PIECE_X_BRAINHEARTS extends Card {

    public PIECE_X_BRAINHEARTS()
    {
        setImageSets("WXDi-P13-005");

        setOriginalName("BRAIN HEARTS");
        setAltNames("ブレインハーツ Burein Haatsu");
        setDescription("jp",
                "カードを２枚引き、手札を１枚捨てる。その後、対戦相手のレベル１のシグニ１体を対象とし、それをバニッシュする。この効果によって捨てたカードが《ディソナアイコン》の場合、代わりに対戦相手のシグニ１体を対象とし、それをバニッシュする。"
        );

        setName("en", "Brain Hearts");
        setDescription("en",
                "Draw two cards and discard a card. Then, vanish target level one SIGNI on your opponent's field. If the card discarded by this effect is #S, instead vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "BRAIN HEARTS");
        setDescription("en_fan",
                "Draw 2 cards, and discard 1 card from your hand. Then, target 1 of your opponent's level 1 SIGNI, and banish it. If the card discarded this way was a #S @[Dissona]@ card, instead target 1 of your opponent's SIGNI, and banish it."
        );

		setName("zh_simplified", "BRAIN HEARTS");
        setDescription("zh_simplified", 
                "抽2张牌，手牌1张舍弃。然后，对战对手的等级1的精灵1只作为对象，将其破坏。因为这个效果舍弃的牌是#S的场合，作为替代，对战对手的精灵1只作为对象，将其破坏。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.PIECE);
        setCost(Cost.colorless(2));
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

            registerPieceAbility(this::onPieceEff);
        }
        
        private void onPieceEff()
        {
            draw(2);
            CardIndex cardIndex = discard(1).get();
            
            TargetFilter filter = new TargetFilter(TargetHint.BANISH).OP().SIGNI();
            if(cardIndex == null || !cardIndex.getIndexedInstance().isState(CardStateFlag.IS_DISSONA)) filter = filter.withLevel(1);
            
            CardIndex target = playerTargetCard(filter).get();
            banish(target);
        }
    }
}
