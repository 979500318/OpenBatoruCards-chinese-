package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.stock.StockAbilityCantAttack;

public final class LRIGA_W2_YukayukaDojaan extends Card {
    
    public LRIGA_W2_YukayukaDojaan()
    {
        setImageSets("WXDi-P06-023");
        
        setOriginalName("ゆかゆか☆どじゃーん");
        setAltNames("ユカユカドジャーン Yukayuka Dojaan");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@C：アタックできない。@@を得る。\n" +
                "@E：このターン終了時、対戦相手が%Xを支払うか手札を１枚捨てないかぎり、このカードをルリグデッキに戻す。"
        );
        
        setName("en", "Yukayuka☆Dojaan");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack.@@until end of turn.\n" +
                "@E: At the end of this turn, return this card to the LRIG deck unless your opponent pays %X or discards a card."
        );
        
        setName("en_fan", "Yukayuka☆Dojaan");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack.@@" +
                "@E: At the end of this turn, unless your opponent pays %X or discards 1 card from their hand, return this card to your LRIG deck."
        );
        
		setName("zh_simplified", "由香香☆咚呛");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "@E 这个回合结束时，如果对战对手不把%X:支付或把手牌1张舍弃，那么这张牌返回分身牌组。\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.WHITE);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);
        
        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH, PlayFormat.CHINESE);
    }
    
    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            callDelayedEffect(ChronoDuration.turnEnd(), () -> {
                if(!pay(getOpponent(), new EnerCost(Cost.colorless(1)), new DiscardCost(0,1)))
                {
                    returnToDeck(getCardIndex(), DeckPosition.TOP);
                }
            });
        }
    }
}
