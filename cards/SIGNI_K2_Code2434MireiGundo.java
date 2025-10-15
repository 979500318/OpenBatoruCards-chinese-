package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.EnerCost;

public final class SIGNI_K2_Code2434MireiGundo extends Card {
    
    public SIGNI_K2_Code2434MireiGundo()
    {
        setImageSets("WXDi-P00-076");
        
        setOriginalName("コード２４３４　郡道美玲");
        setAltNames("コードニジサンジグンドウミレイ Koodo Nijisanji Gundou Mirei");
        setDescription("jp",
                "@E %K：あなたのデッキの上からカードを３枚トラッシュに置く。その後、あなたのトラッシュから＜バーチャル＞のシグニ１枚を対象とし、それを場に出す。"
        );
        
        setName("en", "Mirei Gundo, Code 2434");
        setDescription("en",
                "@E %K: Put the top three cards of your deck into the trash. Then, put target level one <<Virtual>> SIGNI from your trash onto the field."
        );
        
        setName("en_fan", "Code 2434 Mirei Gundo");
        setDescription("en_fan",
                "@E %K: Put the top 3 cards of your deck into the trash. Then, target 1 level 1 <<Virtual>> SIGNI from your trash, and put it onto the field."
        );
        
		setName("zh_simplified", "2434代号 郡道美玲 ");
        setDescription("zh_simplified", 
                "@E %K:从你的牌组上面把3张牌放置到废弃区。然后，从你的废弃区把等级1的<<バーチャル>>精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1)), this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            millDeck(3);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().withLevel(1).withClass(CardSIGNIClass.VIRTUAL).fromTrash().playable()).get();
            putOnField(target);
        }
    }
}
