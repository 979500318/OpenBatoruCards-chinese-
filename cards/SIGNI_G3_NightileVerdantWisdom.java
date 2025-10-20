package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.rulechecks.RuleCheck.RuleCheckState;
import open.batoru.core.gameplay.rulechecks.card.RuleCheckCanPowerBeChanged;
import open.batoru.core.gameplay.rulechecks.card.CardRuleCheckRegistry.CardRuleCheckType;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.RuleCheckModifier;
import open.batoru.data.ability.stock.StockAbilityLancer;

public final class SIGNI_G3_NightileVerdantWisdom extends Card {
    
    public SIGNI_G3_NightileVerdantWisdom()
    {
        setImageSets("WXDi-P07-085");
        
        setOriginalName("翠英　ナイチール");
        setAltNames("スイエイナイチール Suiei Naichiiru");
        setDescription("jp",
                "@A %G %G：以下の３つから１つを選ぶ。\n" +
                "$$1ターン終了時まで、このシグニは基本パワーが5000になり、【ランサー】を得る。\n" +
                "$$2ターン終了時まで、このシグニは基本パワーが10000になり、@>@C：対戦相手の効果によってこのシグニのパワーは－（マイナス）されない。@@を得る。\n" +
                "$$3ターン終了時まで、このシグニは基本パワーが12000になり、@>@C：対戦相手の効果によってこのシグニはダウンしない。@@を得る。"
        );
        
        setName("en", "Nightile, Jade Wisdom");
        setDescription("en",
                "@A %G %G: Choose one of the following.\n" +
                "$$1 This SIGNI's base power becomes 5000 and it gains [[Lancer]] until end of turn.\n" +
                "$$2 This SIGNI's base power becomes 10000 and it gains@>@C: This SIGNI's power cannot get -- power by your opponent's effects.@@until end of turn.\n" +
                "$$3 This SIGNI's base power becomes 12000 and it gains@>@C: This SIGNI cannot be downed by your opponent's effects.@@until end of turn."
        );
        
        setName("en_fan", "Nightile, Verdant Wisdom");
        setDescription("en_fan",
                "@A %G %G: @[@|Choose 1 of the following:|@]@\n" +
                "$$1 Until end of turn, this SIGNI's base power becomes 5000, and it gains [[Lancer]].\n" +
                "$$2 Until end of turn, this SIGNI's base power becomes 10000, and it gains:" +
                "@>@C: The power of this SIGNI can't be -- (minus) by your opponent's effects.@@" +
                "$$3 Until end of turn, this SIGNI's base power becomes 12000, and it gains:" +
                "@>@C: Can't be downed by your opponent's effects."
        );
        
		setName("zh_simplified", "翠英 南丁格尔");
        setDescription("zh_simplified", 
                "@A %G %G:从以下的3种选1种。\n" +
                "$$1 直到回合结束时为止，这只精灵的基本力量变为5000，得到[[枪兵]]。\n" +
                "$$2 直到回合结束时为止，这只精灵的基本力量变为10000，得到\n" +
                "@>@C :不会因为对战对手的效果把这只精灵的力量-（减号）。@@\n" +
                "$$3 直到回合结束时为止，这只精灵的基本力量变为12000，得到\n" +
                "@>@C :不会因为对战对手的效果横置。@@\n"
        );

        setType(CardType.SIGNI);
        setColor(CardColor.GREEN);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.WISDOM);
        setLevel(3);
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
            
            registerActionAbility(new EnerCost(Cost.color(CardColor.GREEN, 2)), this::onActionEff);
        }
        
        private void onActionEff()
        {
            switch(playerChoiceMode())
            {
                case 1<<0:
                {
                    setBasePower(getCardIndex(), 5000, ChronoDuration.turnEnd());
                    attachAbility(getCardIndex(), new StockAbilityLancer(), ChronoDuration.turnEnd());
                    
                    break;
                }
                case 1<<1:
                {
                    setBasePower(getCardIndex(), 10000, ChronoDuration.turnEnd());
                    
                    ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_POWER_BE_CHANGED, data -> {
                        return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) && RuleCheckCanPowerBeChanged.getDataAddValue(data) < 0 ?
                                RuleCheckState.BLOCK : RuleCheckState.IGNORE;
                    }));
                    attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
                    
                    break;
                }
                case 1<<2:
                {
                    setBasePower(getCardIndex(), 12000, ChronoDuration.turnEnd());
                    
                    ConstantAbility attachedConst = new ConstantAbility(new RuleCheckModifier<>(CardRuleCheckType.CAN_BE_DOWNED, data -> {
                        return data.getSourceAbility() != null && !isOwnCard(data.getSourceCardIndex()) ? RuleCheckState.BLOCK : RuleCheckState.IGNORE;
                    }));
                    attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
                    
                    break;
                }
            }
        }
    }
}
