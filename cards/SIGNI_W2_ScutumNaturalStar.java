package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.data.DataTable;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityCantAttack;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W2_ScutumNaturalStar extends Card {

    public SIGNI_W2_ScutumNaturalStar()
    {
        setImageSets("WXDi-P09-053");

        setOriginalName("羅星　タテーザ");
        setAltNames("ラセイタテーザ Rasei Tateeza");
        setDescription("jp",
                "@E：あなたのレベル１のシグニを２体まで対象とし、次の対戦相手のターン終了時まで、それらは@>@C：対戦相手のターンの間、[[シャドウ（レベル２以下のシグニ）]]を得る。@@を得る。" +
                "~#：対戦相手のシグニ１体を対象とし、ターン終了時まで、それは@>@>@C@#：アタックできない。@@@@を得る。カードを１枚引く。"
        );

        setName("en", "Scutum, Natural Planet");
        setDescription("en",
                "@E: Up to two target level one SIGNI on your field gain@>@C: During your opponent's turn, this SIGNI gains [[Shadow -- Level two or less SIGNI]].@@until the end of your opponent's next end phase." +
                "~#Target SIGNI on your opponent's field gains@>@C@#: This SIGNI cannot attack.@@@@until end of turn. Draw a card."
        );
        
        setName("en_fan", "Scutum, Natural Planet");
        setDescription("en_fan",
                "@E: Target up to 2 of your level 1 SIGNI, and until the end of your opponent's next turn, they gain:" +
                "@>@C: During your opponent's turn, this SIGNI gains [[Shadow (level 2 or lower SIGNI)]].@@" +
                "~#Target 1 of your opponent's SIGNI, and until end of turn, it gains:" +
                "@>@C@#: Can't attack.@@@@" +
                "Draw 1 card."
        );

		setName("zh_simplified", "罗星 盾牌座");
        setDescription("zh_simplified", 
                "@E :你的等级1的精灵2只最多作为对象，直到下一个对战对手的回合结束时为止，这些得到\n" +
                "@>@C :对战对手的回合期间，得到[[暗影（等级2以下的精灵）]]。@@" +
                "~#对战对手的精灵1只作为对象，直到回合结束时为止，其得到\n" +
                "@>@C :不能攻击。@@\n" +
                "。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.SPACE);
        setLevel(2);
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

            registerEnterAbility(this::onEnterEff);
            
            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private void onEnterEff()
        {
            DataTable<CardIndex> data = playerTargetCard(0,2, new TargetFilter(TargetHint.ABILITY).own().SIGNI().withLevel(1));
            
            if(data.get() != null)
            {
                for(int i=0;i<data.size();i++)
                {
                    ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
                    attachedConst.setCondition(this::onAttachedConstEffCond);
                    
                    attachAbility(data.get(i), attachedConst, ChronoDuration.nextTurnEnd(getOpponent()));
                }
            }
        }
        private ConditionState onAttachedConstEffCond()
        {
            return !isOwnTurn() ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityShadow(this::onAttachedStockEffAddCond));
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                    cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }
        
        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).OP().SIGNI()).get();
            if(target != null) attachAbility(target, new StockAbilityCantAttack(), ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
