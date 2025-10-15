package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_ScrewCarmillaAzureDevil extends Card {
    
    public SIGNI_B2_ScrewCarmillaAzureDevil()
    {
        setImageSets("WXDi-D05-014");
        
        setOriginalName("蒼魔　スクリュー・カーミラ");
        setAltNames("ソウマスクリューカーミラ Souma Sukuryuu Kaamira");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、それを凍結する。あなたのデッキの上からカードを２枚トラッシュに置く。"
        );
        
        setName("en", "Carmilla Screw, Azure Evil");
        setDescription("en",
                "@E: Freeze target SIGNI on your opponent's field. Put the top two cards of your deck into your trash."
        );
        
        setName("en_fan", "Screw Carmilla, Azure Devil");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and freeze it. Put the top 2 cards of your deck into the trash."
        );
        
		setName("zh_simplified", "苍魔 螺旋·卡米拉");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，将其冻结。从你的牌组上面把2张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(8000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FREEZE).OP().SIGNI()).get();
            freeze(target);
            
            millDeck(2);
        }
    }
}
