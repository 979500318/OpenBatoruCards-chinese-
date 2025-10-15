package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.DiscardCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;

public final class LRIGA_W1_YukayukaZubaan extends Card {
    
    public LRIGA_W1_YukayukaZubaan()
    {
        setImageSets("WXDi-P05-022");
        
        setOriginalName("ゆかゆか☆ずばーん");
        setAltNames("ユカユカズバーン Yukayuka Zubaan");
        setDescription("jp",
                "@E：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@U：手札を１枚捨てないかぎりアタックできない。@@を得る。"
        );
        
        setName("en", "Yukayuka☆Zubaan");
        setDescription("en",
                "@E: Target SIGNI on your opponent's field gains@>@C: This SIGNI cannot attack unless you discard a card.@@until end of turn."
        );
        
        setName("en_fan", "Yukayuka☆Zubaan");
        setDescription("en_fan",
                "@E: Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C: Can't attack unless you discard 1 card from your hand."
        );
        
		setName("zh_simplified", "由香香☆哫乓");
        setDescription("zh_simplified", 
                "@E :对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :如果不把手牌1张舍弃，那么不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.YUKAYUKA);
        setLRIGTeam(CardLRIGTeam.KYURUKYURUN);
        setColor(CardColor.WHITE);
        setLevel(1);
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
            
            registerEnterAbility(this::onEnterEff);
        }
        
        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            
            if(target != null)
            {
                ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.COST_TO_ATTACK, data -> {
                    return new DiscardCost(1);
                }));
                
                attachAbility(target, attachedConst, ChronoDuration.turnEnd());
            }
        }
    }
}
