package open.batoru.data.cards;

import open.batoru.core.Deck.DeckPosition;
import open.batoru.core.gameplay.CardIndex;
import open.batoru.core.gameplay.ChronoDuration;
import open.batoru.core.gameplay.GameConst.CardStateFlag;
import open.batoru.core.gameplay.GameConst.GameEventId;
import open.batoru.core.gameplay.GameConst.GamePhase;
import open.batoru.core.gameplay.pickers.TargetFilter;
import open.batoru.core.gameplay.pickers.TargetFilter.TargetHint;
import open.batoru.data.Card;
import open.batoru.data.CardConst.*;
import open.batoru.data.ability.AutoAbility;
import open.batoru.data.ability.AbilityCondition.ConditionState;
import open.batoru.data.ability.cost.TrashCost;
import open.batoru.data.ability.stock.StockAbilityShadow;

public final class SIGNI_W3_Code2434KokoroAmamiya extends Card {

    public SIGNI_W3_Code2434KokoroAmamiya()
    {
        setImageSets("WXDi-CP01-026");

        setOriginalName("コード２４３４　天宮こころ");
        setAltNames("コードニジサンジアマミヤココロ Koodo Nijisanji Amamiya Kokoro");
        setDescription("jp",
                "@U：あなたのターン終了時、あなたのシグニのうち最もパワーの低いシグニ１体を対象とし、次の対戦相手のターン終了時まで、それは[[シャドウ（レベル２以下のシグニ）]]を得る。あなたのライフクロスの枚数が対戦相手のライフクロスの枚数以下の場合、代わりにそれは【シャドウ】を得る。\n" +
                "@E @[エナゾーンから＜バーチャル＞のシグニ２枚をトラッシュに置く]@：あなたのトラッシュから#Gを持つシグニ１枚を対象とし、それを手札に加える。" +
                "~#：対戦相手のパワー10000以下のシグニ１体を対象とし、それを手札に戻す。"
        );

        setName("en", "Amamiya Kokoro, Code 2434");
        setDescription("en",
                "@U: At the end of your turn, target SIGNI on your field with the least power gains [[Shadow -- Level two or less SIGNI]] until the end of your opponent's next end phase. If you have Life Cloth less than or equal to your opponent's Life Cloth, instead it gains [[Shadow]].\n@E @[Put two <<Virtual>> SIGNI from your Ener Zone into your trash]@: Add target SIGNI with a #G from your trash to your hand." +
                "~#Return target SIGNI on your opponent's field with power 10000 or less to its owner's hand."
        );
        
        setName("en_fan", "Code 2434 Kokoro Amamiya");
        setDescription("en_fan",
                "@U: At the end of your turn, target 1 of your SIGNI with the lowest power, and until the end of your opponent's next turn, it gains [[Shadow (level 2 or lower SIGNI)]]. If your life cloth is less than or equal to your opponent's life cloth, it gains [[Shadow]] instead.\n" +
                "@E @[Put 2 <<Virtual>> SIGNI from your ener zone into the trash]@: Target 1 #G @[Guard]@ SIGNI from your trash, and add it to your hand." +
                "~#Target 1 of your opponent's SIGNI with power 10000 or less, and return it to their hand."
        );

		setName("zh_simplified", "2434代号 天宫心");
        setDescription("zh_simplified", 
                "@U :你的回合结束时，你的精灵中力量最低的精灵1只作为对象，直到下一个对战对手的回合结束时为止，其得到[[暗影（等级2以下的精灵）]]。你的生命护甲的张数在对战对手的生命护甲的张数以下的场合，作为替代，其得到[[暗影]]。\n" +
                "@E :从能量区把<<バーチャル>>精灵2张放置到废弃区从你的废弃区把持有#G的精灵1张作为对象，将其加入手牌。" +
                "~#对战对手的力量10000以下的精灵1只作为对象，将其返回手牌。\n"
        );

        setCardFlags(CardFlag.LIFEBURST);

        setType(CardType.SIGNI);
        setColor(CardColor.WHITE);
        setSIGNIClass(CardSIGNIClassGeneration.PERFORMER, CardSIGNIClass.VIRTUAL);
        setLevel(3);
        setPower(12000);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }

    @Override
    public IndexedInstance newIndexedInstance(int cardId) { return new IndexedInstance(cardId); }
    public class IndexedInstance extends Card.IndexedInstance
    {
        public IndexedInstance(int cardId)
        {
            super(cardId);
            
            AutoAbility auto = registerAutoAbility(GameEventId.PHASE_START, this::onAutoEff);
            auto.setCondition(this::onAutoEffCond);
            
            registerEnterAbility(new TrashCost(2, new TargetFilter().SIGNI().withClass(CardSIGNIClass.VIRTUAL).fromEner()), this::onEnterEff);

            registerLifeBurstAbility(this::onLifeBurstEff);
        }

        private ConditionState onAutoEffCond()
        {
            return isOwnTurn() && getCurrentPhase() == GamePhase.END ? ConditionState.OK : ConditionState.BAD;
        }
        private void onAutoEff(CardIndex caller)
        {
            double min = getSIGNIOnField(getOwner()).stream().mapToDouble(c -> ((CardIndex)c).getIndexedInstance().getPower().getValue()).min().orElse(0);
            
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.ABILITY).own().SIGNI().withPower(min)).get();
            if(target != null)
            {
                attachAbility(target, getLifeClothCount(getOwner()) > getLifeClothCount(getOpponent()) ?
                    new StockAbilityShadow(this::onAttachedStockEffAddCond) : new StockAbilityShadow(), ChronoDuration.nextTurnEnd(getOpponent())
                );
            }
        }
        private ConditionState onAttachedStockEffAddCond(CardIndex cardIndexSource)
        {
            return CardType.isSIGNI(cardIndexSource.getCardReference().getType()) &&
                   cardIndexSource.getIndexedInstance().getLevel().getValue() <= 2 ? ConditionState.OK : ConditionState.BAD;
        }

        private void onEnterEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).own().SIGNI().withState(CardStateFlag.CAN_GUARD).fromTrash()).get();
            addToHand(target);
        }

        private void onLifeBurstEff()
        {
            CardIndex target = playerTargetCard(new TargetFilter(TargetHint.HAND).OP().SIGNI().withPower(0,10000)).get();
            addToHand(target);
        }
    }
}
