package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_B2_FocalorAzureDevil extends Card {
    
    public SIGNI_B2_FocalorAzureDevil()
    {
        setImageSets("WXDi-P01-066");
        
        setOriginalName("蒼魔　フォカロル");
        setAltNames("ソウマ フォカロル Souma Fokaroru");
        setDescription("jp",
                "~#：対戦相手のシグニを２体まで対象とし、それらをダウンする。カードを１枚引く。"
        );
        
        setName("en", "Focalor, Azure Evil");
        setDescription("en",
                "~#Down up to two target SIGNI on your opponent's field. Draw a card."
        );
        
        setName("en_fan", "Focalor, Azure Devil");
        setDescription("en_fan",
                "~#Target up to 2 of your opponent's SIGNI, and down them. Draw 1 card."
        );
        
		setName("zh_simplified", "苍魔 佛卡洛");
        setDescription("zh_simplified", 
                "~#对战对手的精灵2只最多作为对象，将这些#D。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onLifeBurstEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.DOWN).OP().SIGNI());
            down(data);
            
            draw(1);
        }
    }
}
