package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardLocation;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.player.PlayerRuleCheckRegistry.PlayerRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PlayerRuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;
import open.batoru.game.FieldData;
import open.batoru.game.gfx.GFX;
import open.batoru.game.gfx.GFXZoneUnderIndicator;

public final class LRIGA_G2_BangSolo extends Card {
    
    public LRIGA_G2_BangSolo()
    {
        setImageSets("WXDi-P03-027");
        
        setOriginalName("バン＝ソロ");
        setAltNames("バンソロ Ban Soro");
        setDescription("jp",
                "@E：あなたのシグニ１体を対象とし、ターン終了時まで、それは[[ランサー]]を得る。\n" +
                "@E %X：次の対戦相手のターン終了時まで、このルリグは@>@C：対戦相手は中央のシグニゾーンにあるシグニでアタックできない。@@を得る。"
        );
        
        setName("en", "Bang =Solo=");
        setDescription("en",
                "@E: Target SIGNI on your field gains [[Lancer]] until end of turn.\n" +
                "@E %X: This LRIG gains@>@C: Your opponent cannot attack with SIGNI in their center SIGNI Zone.@@until the end of your opponent's next end phase."
        );
        
        setName("en_fan", "Bang-Solo");
        setDescription("en_fan",
                "@E: Target 1 of your SIGNI, and until end of turn, it gains [[Lancer]].\n" +
                "@E %X: Until the end of your opponent's next turn, this LRIG gains:" +
                "@>@C: Your opponent can't attack with the SIGNI in their center SIGNI zone."
        );
        
		setName("zh_simplified", "梆=独奏");
        setDescription("zh_simplified", 
                "@E :你的精灵1只作为对象，直到回合结束时为止，其得到[[枪兵]]。\n" +
                "@E %X:直到下一个对战对手的回合结束时为止，这只分身得到\n" +
                "@>@C :对战对手的中央的精灵区的精灵不能攻击。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.BANG);
        setLRIGTeam(CardLRIGTeam.UNIVERSE_BEGINNING);
        setColor(CardColor.GREEN);
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN);
        
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
            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff2);
        }
        
        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityLancer(), ChronoDuration.turnEnd());
        }
        
        private void onEnterEff2()
        {
            ConstantAbility attachedConst = new ConstantAbility(new PlayerRuleCheckModifier<>(PlayerRuleCheckType.CAN_ATTACK, TargetFilter.HINT_OWNER_OP, data ->
                data.getSourceCardIndex().getLocation() == CardLocation.SIGNI_CENTER ? RuleCheckState.BLOCK : RuleCheckState.IGNORE)
            );
            
            GFX.attachToAbility(attachedConst, new GFXZoneUnderIndicator(getOpponent(),CardLocation.SIGNI_CENTER, "chain"));
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
        }
    }
}
