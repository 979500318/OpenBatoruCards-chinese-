package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SPELL_B_INVESTIGATE extends Card {

    public SPELL_B_INVESTIGATE()
    {
        setImageSets("WXDi-P10-063");
        setLinkedImageSets("WXDi-P10-040");

        setOriginalName("INVESTIGATE");
        setAltNames("インベスティゲイト Inbesutigeito");
        setDescription("jp",
                "あなたのデッキの上からカードを３枚見る。その中から１枚を手札に加え、残りを好きな順番でデッキの一番上に戻す。このスペルをチェックゾーンからあなたの《コードオーダー　エルドラ//メモリア》１体の下に置いてもよい。" +
                "~#：対戦相手のシグニ１体を対象とし、それをダウンし凍結する。カードを１枚引く。"
        );

        setName("en", "INVESTIGATE");
        setDescription("en",
                "Look at the top three cards of your deck. Add a card from among them to your hand and put the rest on top of your deck in any order. You may put this spell from the Check Zone under an \"Eldora//Memoria, Code: Order\" on your field." +
                "~#Down target SIGNI on your opponent's field and freeze it. Draw a card."
        );
        
        setName("en_fan", "INVESTIGATE");
        setDescription("en_fan",
                "Look at the top 3 cards of your deck. Add 1 of them to your hand, and put the rest on the top of your deck in any order. You may put this spell from your check zone under 1 of your \"Code Order Eldora//Memoria\"." +
                "~#Target 1 of your opponent's SIGNI, and down and freeze it. Draw 1 card."
        );

		setName("zh_simplified", "INVESTIGATE");
        setDescription("zh_simplified", 
                "从你的牌组上面看3张牌。从中把1张加入手牌，剩下的任意顺序返回牌组最上面。可以把这张魔法从检查区放置到你的《コードオーダー　エルドラ//メモリア》1只的下面。" +
                "~#对战对手的精灵1只作为对象，将其横置并冻结。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLUE);
        setCost(Cost.color(CardColor.BLUE, 1));

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
            look(3);
            
            CardIndex cardIndex = playerTargetCard(new TargetFilter(TargetHint.HAND).own().fromLooked()).get();
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.TOP).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.TOP);
            }
            
            if(playerChoiceActivate())
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.UNDER).own().SIGNI().withName("コードオーダー　エルドラ//メモリア")).get();
                if(cardIndex != null) attach(cardIndex, getCardIndex(), CardUnderType.UNDER_GENERIC);
            }
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            down(target);
            freeze(target);
            
            draw(1);
        }
    }
}
