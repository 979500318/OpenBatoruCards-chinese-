package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.ConstantAbility;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.AbilityGainModifier;
import open.batoru.data.ability.stock.StockAbilityAssassin;

public final class SIGNI_B1_FutaseDissonaAzureBeauty extends Card {

    public SIGNI_B1_FutaseDissonaAzureBeauty()
    {
        setImageSets("WXDi-P13-069");

        setOriginalName("蒼美　ふたせ//ディソナ");
        setAltNames("ソウビフタセディソナ Soubi Futase Disona");
        setDescription("jp",
                "@U：このシグニがアタックしたとき、あなたの場に他の#Sのシグニがある場合、カードを１枚引き、手札を１枚捨てる。\n" +
                "@E %B：ターン終了時まで、このシグニは@>@C：このシグニの正面のシグニが、凍結状態でパワーが5000以下であるかぎり、このシグニは【アサシン】を得る。@@を得る。"
        );

        setName("en", "Futase//Dissona, Azure Beauty");
        setDescription("en",
                "@U: Whenever this SIGNI attacks, if there is another #S SIGNI on your field, draw a card and discard a card.\n@E %B: This SIGNI gains@>@C: As long as the SIGNI in front of this SIGNI is frozen and has power 5000 or less, this SIGNI gains [[Assassin]].@@until end of turn."
        );
        
        setName("en_fan", "Futase//Dissona, Azure Beauty");
        setDescription("en_fan",
                "@U: Whenever this SIGNI attacks, if there is another #S @[Dissona]@ SIGNI on your field, draw 1 card, and discard 1 card from your hand.\n" +
                "@E %B: Until end of turn, this SIGNI gains:" +
                "@>@C: As long as there is a frozen SIGNI with power 5000 or less in front of this SIGNI, this SIGNI gains [[Assassin]]."
        );

		setName("zh_simplified", "苍美 二濑//失调");
        setDescription("zh_simplified", 
                "@U 当这只精灵攻击时，你的场上有其他的#S的精灵的场合，抽1张牌，手牌1张舍弃。\n" +
                "@E %B:直到回合结束时为止，这只精灵得到\n" +
                "@>@C :这只精灵的正面的精灵是，冻结状态且力量在5000以下时，这只精灵得到[[暗杀]]。@@\n"
        );

        setCardFlags(CardFlag.DISSONA);

        setType(CardType.SIGNI);
        setColor(CardColor.BLUE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.BEAUTIFUL_TECHNIQUE);
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

            registerAutoAbility(GameEventId.ATTACK, this::onAutoEff);
            
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLUE, 1)), this::onEnterEff);
        }
        
        private void onAutoEff()
        {
            if(new TargetFilter().own().SIGNI().dissona().except(getCardIndex()).getValidTargetsCount() > 0)
            {
                draw(1);
                discard(1);
            }
        }
        
        private void onEnterEff()
        {
            ConstantAbility attachedConst = new ConstantAbility(new AbilityGainModifier(this::onAttachedConstEffModGetSample));
            attachedConst.setCondition(this::onAttachedConstEffCond);
            
            attachAbility(getCardIndex(), attachedConst, ChronoDuration.turnEnd());
        }
        private ConditionState onAttachedConstEffCond()
        {
            return getOppositeSIGNI() != null &&
                   getOppositeSIGNI().getIndexedInstance().isState(CardStateFlag.FROZEN) &&
                   getOppositeSIGNI().getIndexedInstance().getPower().getValue() <= 5000 ? ConditionState.OK : ConditionState.BAD;
        }
        private Ability onAttachedConstEffModGetSample(CardIndex cardIndex)
        {
            return cardIndex.getIndexedInstance().registerStockAbility(new StockAbilityAssassin());
        }
    }
}
