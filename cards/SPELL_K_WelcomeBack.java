package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.CardColor;
import open.batoru.data.CardConst.CardFlag;
import open.batoru.data.CardConst.CardType;
import open.batoru.data.CardConst.PlayFormat;
import open.batoru.data.Cost;

public final class SPELL_K_WelcomeBack extends Card {

    public SPELL_K_WelcomeBack()
    {
        setImageSets("WXDi-P10-077");
        setLinkedImageSets("WXDi-P10-044","WXDi-P10-060","WXDi-P10-068");

        setOriginalName("ウェルカム・バック");
        setAltNames("ウェルカムバック Uerukamu Bakku");
        setDescription("jp",
                "あなたのデッキの上からカードを２枚トラッシュに置く。その後、あなたのトラッシュから《コードラビリンス　ムジカ//メモリア》か《コードアンチ　マドカ//メモリア》か《ツヴァイ＝サンガ//メモリア》か黒のシグニ１枚を対象とし、それを手札に加える。" +
                "~#：あなたのトラッシュからシグニ１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Welcome Back");
        setDescription("en",
                "Put the top two cards of your deck into your trash. Then, add target \"Muzica//Memoria, Code: Labyrinth\", \"Madoka//Memoria, Code: Anti\", \"Sanga//Memoria Type: Zwei\", or black SIGNI from your trash to your hand." +
                "~#Add target SIGNI from your trash to your hand."
        );
        
        setName("en_fan", "Welcome Back");
        setDescription("en_fan",
                "Put the top 2 cards of your deck into the trash. Then, target 1 \"Code Labyrinth Muzica//Memoria\", \"Code Anti Madoka//Memoria\", or \"Zwei-``Sanga//Memoria\", or 1 black SIGNI from your trash, and add it to your hand." +
                "~#Target 1 SIGNI from your trash, and add it to your hand."
        );

		setName("zh_simplified", "欢迎·回家");
        setDescription("zh_simplified", 
                "从你的牌组上面把2张牌放置到废弃区。然后，从你的废弃区把《コードラビリンス　ムジカ//メモリア》或《コードアンチ　マドカ//メモリア》或《ツヴァイ＝サンガ//メモリア》或黑色的精灵1张作为对象，将其加入手牌。" +
                "~#从你的废弃区把精灵1张作为对象，将其加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SPELL);
        setColor(CardColor.BLACK);
        setCost(Cost.color(CardColor.BLACK, 1));

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
            millDeck(2);
            
            CardIndex target = playerTargetCard(new TargetFilter().own().SIGNI().
             or(new TargetFilter().withName("コードラビリンス　ムジカ//メモリア", "コードアンチ　マドカ//メモリア", "ツヴァイ＝サンガ//メモリア"),
                new TargetFilter().withColor(CardColor.BLACK)
             ).fromTrash()).get();
            addToHand(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().fromTrash()).get();
            addToHand(target);
        }
    }
}
