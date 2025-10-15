package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.AbilityConst.AbilityGain;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.EnerCost;
import open.batoru.data.ability.modifiers.PowerModifier;

public final class SIGNI_W2_Code2434UtakoSuzuka extends Card {

    public SIGNI_W2_Code2434UtakoSuzuka()
    {
        setImageSets("WXDi-CP01-037");

        setOriginalName("コード２４３４　鈴鹿詩子");
        setAltNames("コードニジサンジスズカウタコ Koodo Nijisanji Suzuka Utako");
        setDescription("jp",
                "@E：あなたの場にレベル１の＜バーチャル＞のシグニがあるかぎり、このシグニのパワーは＋5000される。\n" +
                "@E %X：あなたのデッキの上からカードを５枚見る。その中からレベル１の＜バーチャル＞のシグニ１枚を公開し手札に加え、残りを好きな順番でデッキの一番下に置く。" +
                "~#：ターン終了時まで、対戦相手のすべてのシグニは能力を失う。カードを１枚引く。"
        );

        setName("en", "Suzuka Utako, Code 2434");
        setDescription("en",
                "@C: As long as there is a level one <<Virtual>> SIGNI on your field, this SIGNI gets +5000 power.\n@E %X: Look at the top five cards of your deck. Reveal a level one <<Virtual>> SIGNI from among them and add it to your hand. Put the rest on the bottom of your deck in any order." +
                "~#All SIGNI on your opponent's field lose their abilities until end of turn. Draw a card."
        );
        
        setName("en_fan", "Code 2434 Utako Suzuka");
        setDescription("en_fan",
                "@C: As long as there is a level 1 <<Virtual>> SIGNI on your field, this SIGNI gets +5000 power.\n" +
                "@E %X: Look at the top 5 cards of your deck. Reveal 1 level 1 <<Virtual>> SIGNI from among them, and add it to your hand, and put the rest on the bottom of your deck in any order." +
                "~#Until end of turn, all of your opponent's SIGNI lose their abilities. Draw 1 card."
        );

		setName("zh_simplified", "2434代号 铃鹿诗子");
        setDescription("zh_simplified", 
                "@C :你的场上有等级1的<<バーチャル>>精灵时，这只精灵的力量+5000。\n" +
                "@E %X:从你的牌组上面看5张牌。从中把等级1的<<バーチャル>>精灵1张公开加入手牌，剩下的任意顺序放置到牌组最下面。" +
                "~#直到回合结束时为止，对战对手的全部的精灵的能力失去。抽1张牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(2);
        setPower(5000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            registerConstantAbility(this::onConstEffCond, new PowerModifier(5000));

            registerEnterAbility(new EnerCost(Cost.colorless(1)), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }
        
        private ConditionState onConstEffCond()
        {
            return new TargetFilter().own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).withLevel(1).getValidTargetsCount() > 0 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onEnterEff()
        {
            look(5);

            CardIndex cardIndex = playerTargetCard(0,1, new TargetFilter(TargetHint.HAND).own().SIGNI().withClass(CardSIGNIClass.VIRTUAL).withLevel(1).fromLooked()).get();
            reveal(cardIndex);
            addToHand(cardIndex);
            
            while(getLookedCount() > 0)
            {
                cardIndex = playerTargetCard(new TargetFilter(TargetHint.BOTTOM).own().fromLooked()).get();
                returnToDeck(cardIndex, DeckPosition.BOTTOM);
            }
        }

        private void onLifeBurstEff()
        {
            disableAllAbilities(getSIGNIOnField(getOpponent()), AbilityGain.ALLOW, ChronoDuration.turnEnd());
            
            draw(1);
        }
    }
}
