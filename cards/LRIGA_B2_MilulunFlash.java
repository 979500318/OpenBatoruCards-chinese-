package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.DataTable;
import open.batoru.data.ability.AbilityConst;

import java.util.List;

public final class LRIGA_B2_MilulunFlash extends Card {

    public LRIGA_B2_MilulunFlash()
    {
        setImageSets("WXDi-P14-027");

        setOriginalName("ミルルン☆フラッシュ");
        setAltNames("ミルルンフラッシュ Mirurun Furasshu");
        setDescription("jp",
                "@E：あなたのトラッシュから#Gを持たないそれぞれ名前の異なるシグニを好きな枚数対象とし、それらをデッキに加えてシャッフルする。その後、対戦相手のシグニを、レベルの合計がこの方法でデッキに加えたシグニの枚数と同じになるように好きな数対象とし、それらをダウンする。"
        );

        setName("en", "Milulun ☆ Flash");
        setDescription("en",
                "@E: Shuffle any number of target SIGNI with different names and without a #G from your trash into your deck. Then, down any number of target SIGNI on your opponent's field with a total level equal to the number of SIGNI added to your deck this way."
        );
        
        setName("en_fan", "Milulun☆Flash");
        setDescription("en_fan",
                "@E: Target any number of SIGNI with different names without #G @[Guard]@ from your trash, and shuffle them into your deck. Then, target any number of your opponent's SIGNI whose total level is equal to the number of shuffled this way SIGNI, and down them."
        );

		setName("zh_simplified", "米璐璐恩☆闪光");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把不持有#G的名字不同的精灵任意张数作为对象，将这些加入牌组洗切。然后，对战对手的精灵，等级的合计与这个方法加入牌组的精灵的张数相同的任意数量作为对象，将这些横置。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.MILULUN);
        setColor(CardColor.BLUE);
        setCost(Cost.colorless(5));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        private int countReturned;
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.SHUFFLE).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash(), this::onEnterEffTargetCond1);
            countReturned = returnToDeck(data, DeckPosition.TOP);
            
            if(countReturned > 0)
            {
                shuffleDeck();
                
                data = playerTargetCard(0,AbilityConst.MAX_UNLIMITED, new TargetFilter(TargetHint.DOWN).OP().SIGNI(), this::onEnterEffTargetCond2);
                down(data);
            }
        }
        private boolean onEnterEffTargetCond1(List<CardIndex> listPickedCards)
        {
            return listPickedCards.stream().map(c -> c.getCardReference().getOriginalName()).distinct().count() == listPickedCards.size();
        }
        private boolean onEnterEffTargetCond2(List<CardIndex> listPickedCards)
        {
            return listPickedCards.stream().mapToInt(c -> c.getIndexedInstance().getLevel().getValue()).sum() == countReturned;
        }
    }
}
