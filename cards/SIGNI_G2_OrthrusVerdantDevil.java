package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_G2_OrthrusVerdantDevil extends Card {
    
    public SIGNI_G2_OrthrusVerdantDevil()
    {
        setImageSets("WXDi-P05-072");
        
        setOriginalName("翠魔　オルトロス");
        setAltNames("スイマオルトロス Suima Orutorosu");
        setDescription("jp",
                "@U：このシグニがバニッシュされたとき、[[エナチャージ１]]をする。\n" +
                "@E：対戦相手は[[エナチャージ１]]をしてもよい。" +
                "~#：対戦相手のシグニ１体を対象とし、%X %Xを支払ってもよい。そうした場合、それをバニッシュする。"
        );
        
        setName("en", "Orthrus, Jade Evil");
        setDescription("en",
                "@U: When this SIGNI is vanished, [[Ener Charge 1]].\n" +
                "@E: Your opponent may [[Ener Charge 1]]." +
                "~#You may pay %X %X. If you do, vanish target SIGNI on your opponent's field."
        );
        
        setName("en_fan", "Orthrus, Verdant Devil");
        setDescription("en_fan",
                "@U: When this SIGNI is banished, [[Ener Charge 1]].\n" +
                "@E: Your opponent may [[Ener Charge 1]]." +
                "~#Target 1 of your opponent's SIGNI, and you may pay %X %X. If you do, banish it."
        );
        
		setName("zh_simplified", "翠魔 俄耳托斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵被破坏时，[[能量填充1]]。\n" +
                "@E :对战对手可以[[能量填充1]]。" +
                "~#对战对手的精灵1只作为对象，可以支付%X %X。这样做的场合，将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(12000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.BANISH, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            enerCharge(1);
        }
        
        private void onEnterEff()
        {
            if(playerChoiceActivate(getOpponent()))
            {
                enerCharge(getOpponent(), 1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.colorless(2)))
            {
                banish(target);
            }
        }
    }
}
