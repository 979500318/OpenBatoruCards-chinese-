package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.GameConst.CardUnderType;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.CoinCost;

public final class SIGNI_R2_CodeRideLaylaMemoria extends Card {
    
    public SIGNI_R2_CodeRideLaylaMemoria()
    {
        setImageSets("WXDi-P07-065", "WXDi-P07-065P");
        
        setOriginalName("コードライド　レイラ//メモリア");
        setAltNames("コードライドレイラメモリア Koodo Raido Reira Memoria");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、ターン終了時まで、このシグニのパワーを自身の下にあるすべてのシグニのパワーの合計と同じだけ＋（プラス）する。その後、このシグニのパワーが15000以上の場合、%R %R %Xを支払ってもよい。そうした場合、対戦相手のシグニ１体を対象とし、それをバニッシュする。\n" +
                "@E：あなたのデッキの一番上のカードをこのシグニの下に置く。\n" +
                "@A #C：あなたのデッキの一番上のカードをこのシグニの下に置く。"
        );
        
        setName("en", "Layla//Memoria, Code: Ride");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, it gets + the total power of all SIGNI underneath this SIGNI until end of turn. Then, if its power is 15000 or more, you may pay %R %R %X. If you do, vanish target SIGNI on your opponent's field.\n" +
                "@E: Put the top card of your deck under this SIGNI.\n" +
                "@A #C: Put the top card of your deck under this SIGNI."
        );
        
        setName("en_fan", "Code Ride Layla//Memoria");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, until end of turn, this SIGNI gains power equal to the total power of SIGNI under this SIGNI. Then, if this SIGNI's power is 15000 or more, target 1 of your opponent's SIGNI, and you may pay %R %R %X. If you do, banish it.\n" +
                "@E: Put the top card of your deck under this SIGNI.\n" +
                "@A #C: Put the top card of your deck under this SIGNI."
        );
        
		setName("zh_simplified", "骑乘代号 蕾拉//回忆");
        setDescription("zh_simplified", 
                "@U :当这只精灵攻击时，直到回合结束时为止，这只精灵的力量+（加号）与自己的下面全部的精灵的力量的合计的相同的数值。然后，这只精灵的力量在15000以上的场合，可以支付%R %R%X。这样做的场合，对战对手的精灵1只作为对象，将其破坏。\n" +
                "@E :你的牌组最上面的牌放置到这只精灵的下面。\n" +
                "@A #C:你的牌组最上面的牌放置到这只精灵的下面。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.RED);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.RIDING_MACHINE);
        setLevel(2);
        setPower(5000);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(this::onEnterEff);
            
            registerActionAbility(new CoinCost(1), this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            gainPower(getCardIndex(), new TargetFilter().own().SIGNI().under(getCardIndex()).getExportedData().stream().mapToDouble(c -> ((CardIndex)c).getCardReference().getPower()).sum(), ChronoDuration.turnEnd());
            
            if(getPower().getValue() >= 15000)
            {
                CardIndex target = playerTargetCard(new TargetFilter(TargetHint.BANISH).OP().SIGNI()).get();
                
                if(target != null && payEner(Cost.color(CardColor.RED, 2) + Cost.colorless(1)))
                {
                    banish(target);
                }
            }
        }
        
        private void onEnterEff()
        {
            attach(getCardIndex(), CardLocation.DECK_MAIN, CardUnderType.UNDER_GENERIC);
        }
    }
}
