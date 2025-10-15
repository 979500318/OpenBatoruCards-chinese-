package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.ActionHint;

public final class SIGNI_K3_DragonMaidPhantomDragonPrincess extends Card {
    
    public SIGNI_K3_DragonMaidPhantomDragonPrincess()
    {
        setImageSets("WXDi-P04-043");
        
        setOriginalName("幻竜姫　ドラゴンメイド");
        setAltNames("ゲンリュウキドラゴンメイド Genryuuki Doragon Meido");
        setDescription("jp",
                "@U：いずれかのプレイヤーがリフレッシュしたとき、対戦相手のシグニ１体を対象とし、%Kを支払ってもよい。そうした場合、ターン終了時まで、それのパワーを－10000する。\n" +
                "@U：このシグニがアタックしたとき、あなたか対戦相手のデッキの上からカードを４枚トラッシュに置く。"
        );
        
        setName("en", "Dragon Maid, Phantom Dragon Queen");
        setDescription("en",
                "@U: Whenever a player refreshes their deck, you may pay %K. If you do, target SIGNI on your opponent's field gets --10000 power until end of turn.\n" +
                "@U: Whenever this SIGNI attacks, put the top four cards of your deck or your opponent's deck into their owner's trash."
        );
        
        setName("en_fan", "Dragon Maid, Phantom Dragon Princess");
        setDescription("en_fan",
                "@U: Whenever any player refreshes, target 1 of your opponent's SIGNI, and you may pay %K. If you do, until end of turn, it gets --10000 power.\n" +
                "@U: Whenever this SIGNI attacks, put the top 4 cards of your or your opponent's deck into the trash."
        );
        
		setName("zh_simplified", "幻龙姬 龙女仆");
        setDescription("zh_simplified", 
                "@U :当任一方的玩家重构时，对战对手的精灵1只作为对象，可以支付%K。这样做的场合，直到回合结束时为止，其的力量-10000。\n" +
                "@U :当这只精灵攻击时，从你或对战对手的牌组上面把4张牌放置到废弃区。\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.BLACK);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.DRAGON_BEAST);
        setLevel(3);
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
            
            registerAutoAbility(GameEventId.REFRESH, this::onAutoEff1);
            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff2);
        }
        
        private void onAutoEff1(CardIndex caller)
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.MINUS).OP().SIGNI()).get();
            
            if(target != null && payEner(Cost.color(CardColor.BLACK, 1)))
            {
                gainPower(target, -10000, ChronoDuration.turnEnd());
            }
        }
        
        private void onAutoEff2()
        {
            millDeck(playerChoiceAction(ActionHint.OWN, ActionHint.OPPONENT) == 1 ? getOwner() : getOpponent(), 4);
        }
    }
}
