package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class SIGNI_K2_ChenGongWickedGeneral extends Card {
    
    public SIGNI_K2_ChenGongWickedGeneral()
    {
        setImageSets("WXDi-P02-085");
        
        setOriginalName("凶将　チンキュウ");
        setAltNames("キョウショウチンキュウ Kyoushou Chinkyuu");
        setDescription("jp",
                "~#：あなたのトラッシュから#Gを持たないシグニを２枚まで対象とし、それらを手札に加える。"
        );
        
        setName("en", "Chen Gong, Doomed General");
        setDescription("en",
                "~#Add up to two target SIGNI without a #G from your trash to your hand."
        );
        
        setName("en_fan", "Chen Gong, Wicked General");
        setDescription("en_fan",
                "~#Target up to 2 SIGNI without #G @[Guard]@ from your trash, and add them to your hand."
        );
        
		setName("zh_simplified", "凶将 陈宫");
        setDescription("zh_simplified", 
                "~#从你的废弃区把不持有#G的精灵2张最多作为对象，将这些加入手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VALOR);
        setLevel(2);
        setPower(10000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
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
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.HAND).own().SIGNI().not(new TargetFilter().withState(CardStateFlag.CAN_GUARD)).fromTrash());
            addToHand(data);
        }
    }
}
