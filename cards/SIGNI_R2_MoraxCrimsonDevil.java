package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameLog;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;

public final class SIGNI_R2_MoraxCrimsonDevil extends Card {
    
    public SIGNI_R2_MoraxCrimsonDevil()
    {
        setImageSets("WXDi-P04-058");
        
        setOriginalName("紅魔　モラクス");
        setAltNames("コウマモラクス Kouma Morakusu");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、このターンにあなたの効果によって対戦相手のエナゾーンからカードが１枚以上トラッシュに置かれていた場合、[[エナチャージ１]]。" +
                "~#：対戦相手のシグニ１体を対象とし、対戦相手が%X %Xを支払わないかぎり、それをバニッシュする。"
        );
        
        setName("en", "Morax, Crimson Evil");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if one or more cards were put from your opponent's Ener Zone into their trash by your effect this turn, [[Ener Charge 1]]." +
                "~#Vanish target SIGNI on your opponent's field unless they pay %X %X."
        );
        
        setName("en_fan", "Morax, Crimson Devil");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if 1 or more cards were put from your opponent's ener zone into the trash by your effect this turn, [[Ener Charge 1]]." +
                "~#Target 1 of your opponent's SIGNI, and unless your opponent pays %X %X, banish it."
        );
        
		setName("zh_simplified", "红魔 摩拉克斯");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，这个回合因为你的效果从对战对手的能量区把牌1张以上放置到废弃区的场合，[[能量填充1]]。" +
                "~#对战对手的精灵1只作为对象，如果对战对手不把%X %X:支付，那么将其破坏。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);
        
        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DEVIL);
        setLevel(2);
        setPower(3000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private void onAutoEff()
        {
            if(GameLog.getTurnRecordsCount(event -> event.getId() == GameEventId.TRASH &&
                event.getSourceAbility() != null && isOwnCard(event.getSource()) &&
                !isOwnCard(event.getCaller()) && event.getCaller().getLocation() == CardLocation.ENER) > 0)
            {
                enerCharge(1);
            }
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
            
            if(target != null && !payEner(getOpponent(), Cost.colorless(2)))
            {
                banish(target);
            }
        }
    }
}
