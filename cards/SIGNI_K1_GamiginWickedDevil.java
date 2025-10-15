package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AutoAbility;

public final class SIGNI_K1_GamiginWickedDevil extends Card {
    
    public SIGNI_K1_GamiginWickedDevil()
    {
        setImageSets("WXDi-P02-082");
        
        setOriginalName("凶魔　ガミジン");
        setAltNames("キョウマガミジン Kyouma Gamijin");
        setDescription("jp",
                "@U：このカードがあなたのデッキからトラッシュに置かれたとき、あなたのターンの場合、対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－3000する。"
        );
        
        setName("en", "Gamigin, Wicked Devil");
        setDescription("en",
                "@U: When this card is put into your trash from your deck, if it's your turn, you may pay %K. If you do, target SIGNI on your opponent's field gets --3000 power until end of turn."
        );
        
        setName("en_fan", "Gamigin, Wicked Devil");
        setDescription("en_fan",
                "@U: When this SIGNI is put from your deck into the trash, if it is your turn, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --3000 power."
        );
        
		setName("zh_simplified", "凶魔 加米基");
        setDescription("zh_simplified", 
                "@U :当这张牌从你的牌组放置到废弃区时，你的回合的场合，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-3000。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(1);
        setPower(1000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.TRASH, this::onAutoEff);
            auto.setActiveLocation(CardLocation.DECK_MAIN);
        }
        
        private void onAutoEff()
        {
            if(isOwnTurn())
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
                {
                    gainPower(target, -3000, ChronoDuration.turnEnd());
                }
            }
        }
    }
}
