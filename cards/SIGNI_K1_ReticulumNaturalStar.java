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

public final class SIGNI_K1_ReticulumNaturalStar extends Card {
    
    public SIGNI_K1_ReticulumNaturalStar()
    {
        setImageSets("WXDi-P03-081");
        
        setOriginalName("羅星　レティクルム");
        setAltNames("ラセイレティクルム Rasei Retikurumu");
        setDescription("jp",
                "@U：このカードがデッキからトラッシュに置かれたとき、手札を１枚捨ててもよい。そうした場合、このカードをトラッシュから手札に加える。" +
                "~#：対戦相手のシグニ１体を対象とし、%K %Xを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－12000する。"
        );
        
        setName("en", "Reticulum, Natural Planet");
        setDescription("en",
                "@U: When this card is put into your trash from your deck, you may discard a card. If you do, add this card from your trash to your hand." +
                "~#You may pay %K %X. If you do, target SIGNI on your opponent's field gets --12000 power until end of turn."
        );
        
        setName("en_fan", "Reticulum, Natural Star");
        setDescription("en_fan",
                "@U: When this SIGNI is put from your deck into the trash, you may discard 1 card from your hand. If you do, add this card from your trash to your hand." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %K %X. If you do, until end of turn, it gets --12000 power."
        );
        
		setName("zh_simplified", "罗星 网罟座");
        setDescription("zh_simplified", 
                "@U :当这张牌从牌组放置到废弃区时，可以把手牌1张舍弃。这样做的场合，这张牌从废弃区加入手牌。" +
                "~#对战对手的精灵1只作为对象，可以支付%K%X。这样做的场合，直到回合结束时为止，其的力量-12000。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(1);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
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
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(getCardIndex().getLocation() == CardLocation.TRASH && discard(0,1).get() != null)
            {
                addToHand(getCardIndex());
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1) + Cost.colorless(1)))
            {
                gainPower(target, -12000, ChronoDuration.turnEnd());
            }
        }
    }
}
