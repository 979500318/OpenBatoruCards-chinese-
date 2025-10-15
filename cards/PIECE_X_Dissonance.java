package open.batoru.data.cards;

import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.PieceAbility;

public final class PIECE_X_Dissonance extends Card {

    public PIECE_X_Dissonance()
    {
        setImageSets("WXDi-P12-004");

        setOriginalName("ディソナンス");
        setAltNames("Disonansu");
        setDescription("jp",
                "あなたのトラッシュから#Sのシグニを２枚まで対象とし、それらを手札に加える。"
        );

        setName("en", "Dissonance");
        setDescription("en",
                "Add up to two target #S SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Dissonance");
        setDescription("en_fan",
                "Target up to 2 #S @[Dissona]@ SIGNI from your trash, and add them to your hand."
        );

		setName("zh_simplified", "失调和弦");
        setDescription("zh_simplified", 
                "从你的废弃区把#S的精灵2张最多作为对象，将这些加入手牌。\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.PIECE);
        setCost(Cost.colorless(1));
        setUseTiming(UseTiming.MAIN);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private final PieceAbility piece;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            piece = registerPieceAbility(this::onPieceEffPreTarget, this::onPieceEff);
        }

        private void onPieceEffPreTarget()
        {
            piece.setTargets(playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().dissona().fromTrash()));
        }
        private void onPieceEff()
        {
            addToHand(piece.getTargets());
        }
    }
}
