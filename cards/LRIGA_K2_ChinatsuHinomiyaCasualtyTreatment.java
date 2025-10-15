package open.batoru.data.cards;

import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.Cost;
import open.batoru.data.ability.Ability.AbilityFlag;
import open.batoru.data.ability.EnterAbility;
import open.batoru.data.ability.cost.EnerCost;

public final class LRIGA_K2_ChinatsuHinomiyaCasualtyTreatment extends Card {

    public LRIGA_K2_ChinatsuHinomiyaCasualtyTreatment()
    {
        setImageSets("WXDi-CP02-049");

        setOriginalName("火宮チナツ[戦傷治療]");
        setAltNames("ヒノミヤチナツセンショウチリョウ Hinomiya Chinatsu Senshou Chiryou");
        setDescription("jp",
                "@E：あなたのトラッシュから##を持たないカード１枚を対象とし、それをライフクロスに加える。\n" +
                "@E %K %X %X %X：あなたのトラッシュから##を持たないカード１枚を対象とし、それをライフクロスに加える。" +
                "~{{E：あなたのトラッシュから＜ブルアカ＞のカード１枚を対象とし、それを手札に加える。"
        );

        setName("en", "Hinomiya Chinatsu [Tactical First Aid]");
        setDescription("en",
                "@E: Add target card without ## from your trash to your Life Cloth.\n@E %K %X %X %X: Add target card without ## from your trash to your Life Cloth.~{{E: Add target <<Blue Archive>> card from your trash to your hand."
        );
        
        setName("en_fan", "Chinatsu Hinomiya [Casualty Treatment]");
        setDescription("en_fan",
                "@E: Target 1 card without ## @[Life Burst]@ from your trash, and add it to life cloth.\n" +
                "@E %K %X %X %X: Target 1 card without ## @[Life Burst]@ from your trash, and add it to life cloth." +
                "~{{E: Target 1 <<Blue Archive>> card from your trash, and add it to your hand."
        );

		setName("zh_simplified", "火宫千夏[战伤治疗]");
        setDescription("zh_simplified", 
                "@E 从你的废弃区把不持有##的牌1张作为对象，将其加入生命护甲。\n" +
                "@E %K%X %X %X从你的废弃区把不持有##的牌1张作为对象，将其加入生命护甲。\n" +
                "~{{E:从你的废弃区把<<ブルアカ>>牌1张作为对象，将其加入手牌。@@\n"
        );

        setType(CardType.LRIG_ASSIST);
        setLRIGType(CardLRIGType.PREFECT_TEAM);
        setColor(CardColor.BLACK);
        setCost(Cost.colorless(1));
        setLevel(2);
        setLimit(+1);
        setUseTiming(UseTiming.MAIN | UseTiming.ATTACK);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);

            registerEnterAbility(this::onEnterEff1);
            registerEnterAbility(new EnerCost(Cost.color(CardColor.BLACK, 1) + Cost.colorless(3)), this::onEnterEff1);

            EnterAbility enter2 = registerEnterAbility(this::onEnterEff2);
            enter2.getFlags().addValue(AbilityFlag.BONDED);
        }

        private void onEnterEff1()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HEAL).own().not(new TargetFilter().lifeBurst()).fromTrash()).get();
            addToLifeCloth(target);
        }

        private void onEnterEff2()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().withClass(CardSIGNIClass.BLUE_ARCHIVE).fromTrash()).get();
            addToHand(target);
        }
    }
}

