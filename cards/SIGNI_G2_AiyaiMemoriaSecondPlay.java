package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.cost.DownCost;

public final class SIGNI_G2_AiyaiMemoriaSecondPlay extends Card {
    
    public SIGNI_G2_AiyaiMemoriaSecondPlay()
    {
        setImageSets("WXDi-P06-075", "WXDi-P06-075P", "SPDi01-80","SPDi38-16");
        
        setOriginalName("弍ノ遊　アイヤイ//メモリア");
        setAltNames("ニノユウアイヤイメモリア Ni no Yuu Aiyai Memoria");
        setDescription("jp",
                "@A #D：あなたのエナゾーンからシグニ１枚を対象とし、それを場に出す。"
        );
        
        setName("en", "Aiyai//Memoria, Second Play");
        setDescription("en",
                "@A #D: Put target SIGNI from your Ener Zone onto your field."
        );
        
        setName("en_fan", "Aiyai//Memoria, Second Play");
        setDescription("en_fan",
                "@A #D: Target 1 SIGNI from your ener zone, and put it onto the field."
        );
        
		setName("zh_simplified", "贰之游 艾娅伊//回忆");
        setDescription("zh_simplified", 
                "@A 横置:从你的能量区把精灵1张作为对象，将其出场。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.PLAYGROUND_EQUIPMENT);
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
            
            registerActionAbility(new DownCost(), this::onActionEff);
        }
        
        private void onActionEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.FIELD).own().SIGNI().fromEner().playable()).get();
            putOnField(target);
        }
    }
}
